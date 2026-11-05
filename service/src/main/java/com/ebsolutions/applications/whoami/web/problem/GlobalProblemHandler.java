package com.ebsolutions.applications.whoami.web.problem;

import lombok.extern.slf4j.Slf4j;
import org.jspecify.annotations.NonNull;
import org.springframework.core.annotation.Order;
import org.springframework.http.*;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.HandlerMethodValidationException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

/**
 * Protocol-level errors for every module: malformed input, unknown URLs, unsupported methods and
 * media types, validation failures, and unexpected crashes. Business errors are handled by each
 * module's own handler, which takes precedence over this one.
 */
@Slf4j
@RestControllerAdvice
@Order
class GlobalProblemHandler extends ResponseEntityExceptionHandler {

  /** Valid request bodies. */
  @Override
  protected ResponseEntity<Object> handleMethodArgumentNotValid(
      MethodArgumentNotValidException ex,
      @NonNull HttpHeaders headers,
      @NonNull HttpStatusCode status,
      @NonNull WebRequest request) {
    ProblemDetail problem = ex.getBody();
    problem.setDetail("The request breaks one or more rules");
    problem.setProperty(
        "violations", ex.getBindingResult().getFieldErrors().stream().map(Violation::of).toList());
    return handleExceptionInternal(ex, problem, headers, status, request);
  }

  /** Validated path variables, query parameters and headers. */
  @Override
  protected ResponseEntity<Object> handleHandlerMethodValidationException(
      HandlerMethodValidationException ex,
      @NonNull HttpHeaders headers,
      @NonNull HttpStatusCode status,
      @NonNull WebRequest request) {
    ProblemDetail problem = ex.getBody();
    problem.setDetail("The request breaks one or more rules");
    problem.setProperty(
        "violations",
        ex.getParameterValidationResults().stream()
            .flatMap(
                result ->
                    result.getResolvableErrors().stream()
                        .map(error -> Violation.of(result.getMethodParameter(), error)))
            .toList());
    return handleExceptionInternal(ex, problem, headers, status, request);
  }

  /** Malformed JSON, or a body that doesn't match the expected shape. */
  @Override
  protected ResponseEntity<Object> handleHttpMessageNotReadable(
      @NonNull HttpMessageNotReadableException ex,
      @NonNull HttpHeaders headers,
      @NonNull HttpStatusCode status,
      @NonNull WebRequest request) {
    ProblemDetail problem =
        ProblemDetail.forStatusAndDetail(
            status, "The request body is not valid JSON or doesn't match the expected shape");
    return handleExceptionInternal(ex, problem, headers, status, request);
  }

  /** Anything nobody else handled: log everything, reveal nothing. */
  @ExceptionHandler(Exception.class)
  ProblemDetail unexpected(Exception ex) {
    log.error("Unexpected error", ex);
    return ProblemDetail.forStatusAndDetail(
        HttpStatus.INTERNAL_SERVER_ERROR, "An unexpected error occurred");
  }
}
