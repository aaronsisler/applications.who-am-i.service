package com.ebsolutions.applications.whoami.core.exception;

import com.ebsolutions.applications.whoami.core.ErrorCodeMapper;
import com.ebsolutions.applications.whoami.core.ErrorMessages;
import com.ebsolutions.applications.whoami.dto.ErrorCode;
import com.ebsolutions.applications.whoami.dto.ErrorDetail;
import com.ebsolutions.applications.whoami.dto.ErrorDto;
import jakarta.validation.ConstraintViolationException;
import java.util.Collections;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.NoHandlerFoundException;

/**
 * Global handler for all exceptions thrown in controllers.
 * Handles validation, HTTP errors, and service-level exceptions,
 * converting them into structured {@link ErrorDto} responses
 * suitable for clients.
 */
@Slf4j
@RestControllerAdvice
@RequiredArgsConstructor
public class GlobalControllerExceptionHandler {

  private final ErrorCodeMapper errorCodeMapper;

  /* ================= 400 – BAD REQUESTS (INPUT / CONTRACT) ================= */

  /**
   * Handles validation errors on request body fields annotated with {@code @Valid}.
   *
   * @param ex the exception thrown by Spring when {@link jakarta.validation.Valid} fails
   * @return 400 {@link ResponseEntity} with {@link ErrorDto} listing field errors
   */
  @ExceptionHandler(MethodArgumentNotValidException.class)
  public ResponseEntity<ErrorDto> handleMethodArgumentNotValid(MethodArgumentNotValidException ex) {
    List<ErrorDetail> errors = ex.getBindingResult()
        .getFieldErrors()
        .stream()
        .map(this::mapFieldError)
        .toList();

    return ResponseEntity
        .badRequest()
        .body(ErrorDto.builder().errors(errors).build());
  }

  /**
   * Handles validation errors thrown on request parameters, path variables, etc.
   *
   * @param ex the {@link ConstraintViolationException} thrown by Spring
   * @return 400 {@link ResponseEntity} with {@link ErrorDto} listing constraint violations
   */
  @ExceptionHandler(ConstraintViolationException.class)
  public ResponseEntity<ErrorDto> handleConstraintViolation(ConstraintViolationException ex) {
    List<ErrorDetail> errors = ex.getConstraintViolations()
        .stream()
        .map(v -> ErrorDetail.builder()
            .field(v.getPropertyPath().toString())
            .code(errorCodeMapper.map(
                v.getConstraintDescriptor().getAnnotation().annotationType().getSimpleName()))
            .message(v.getMessage())
            .build())
        .toList();

    return ResponseEntity
        .badRequest()
        .body(ErrorDto.builder().errors(errors).build());
  }

  /**
   * Handles malformed JSON or unreadable request bodies.
   *
   * @return 400 {@link ResponseEntity} with single {@link ErrorDetail} for malformed input
   */
  @ExceptionHandler(HttpMessageNotReadableException.class)
  public ResponseEntity<ErrorDto> handleMessageNotReadable() {
    return ResponseEntity
        .badRequest()
        .body(ErrorDto.builder()
            .errors(Collections.singletonList(
                ErrorDetail.builder()
                    .code(ErrorCode.MALFORMED_JSON)
                    .message(ErrorMessages.MESSAGE_NOT_READABLE.message())
                    .build()))
            .build());
  }

  /* ================= 404 – URL or PATH NOT FOUND ================= */

  /**
   * Handles requests where no matching endpoint is found.
   *
   * @return 404 {@link ResponseEntity} with single {@link ErrorDetail}
   */
  @ExceptionHandler(NoHandlerFoundException.class)
  public ResponseEntity<ErrorDto> handleNoHandlerFound() {

    return ResponseEntity
        .status(HttpStatus.NOT_FOUND)
        .body(ErrorDto.builder()
            .errors(Collections.singletonList(
                ErrorDetail.builder()
                    .code(ErrorCode.NOT_FOUND)
                    .message(ErrorMessages.HTTP_METHOD_NOT_ALLOWED.message())
                    .build()))
            .build());
  }

  /* ================= 405 – METHOD NOT ALLOWED ================= */

  /**
   * Handles requests using unsupported HTTP methods.
   *
   * @return 405 {@link ResponseEntity} with single {@link ErrorDetail}
   */
  @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
  public ResponseEntity<ErrorDto> handleMethodNotSupported() {
    return ResponseEntity
        .status(HttpStatus.METHOD_NOT_ALLOWED)
        .body(ErrorDto.builder()
            .errors(Collections.singletonList(
                ErrorDetail.builder()
                    .code(ErrorCode.METHOD_NOT_ALLOWED)
                    .message(ErrorMessages.HTTP_METHOD_NOT_ALLOWED.message())
                    .build()))
            .build());
  }

  /* ================= 409 – CONFLICT ================= */

  /**
   * Handles unique constraint violations in the data store.
   *
   * @param ex the {@link DuplicateDataException} thrown by service
   * @return 409 {@link ResponseEntity} with single {@link ErrorDetail}
   */
  @ExceptionHandler(DuplicateDataException.class)
  public ResponseEntity<ErrorDto> handleDuplicateData(DuplicateDataException ex) {
    return ResponseEntity
        .status(HttpStatus.CONFLICT)
        .body(ErrorDto.builder()
            .errors(Collections.singletonList(
                ErrorDetail.builder()
                    .code(ErrorCode.EMAIL_ALREADY_EXISTS)
                    .message(ex.getMessage())
                    .build()))
            .build());
  }

  /* ================= 415 – UNSUPPORTED MEDIA TYPE ================= */

  /**
   * Handles requests with unsupported Content-Type headers.
   *
   * @return 415 {@link ResponseEntity} with single {@link ErrorDetail}
   */
  @ExceptionHandler(HttpMediaTypeNotSupportedException.class)
  public ResponseEntity<ErrorDto> handleUnsupportedMedia() {
    return ResponseEntity
        .status(HttpStatus.UNSUPPORTED_MEDIA_TYPE)
        .body(ErrorDto.builder()
            .errors(Collections.singletonList(
                ErrorDetail.builder()
                    .code(ErrorCode.UNSUPPORTED_MEDIA_TYPE)
                    .message(ErrorMessages.UNSUPPORTED_CONTENT_TYPE.message())
                    .build()))
            .build());
  }

  /* ================= 500 – INTERNAL_SERVER_ERROR ================= */

  /**
   * Handles exceptions caused by unavailable data store.
   *
   * @param ex the exception thrown when persistence fails
   * @return 503 {@link ResponseEntity} with single {@link ErrorDetail}
   */
  @ExceptionHandler(DataStoreException.class)
  public ResponseEntity<ErrorDto> handleDataStoreUnavailable(DataStoreException ex) {
    return ResponseEntity
        .status(HttpStatus.SERVICE_UNAVAILABLE)
        .body(ErrorDto.builder()
            .errors(Collections.singletonList(
                ErrorDetail.builder()
                    .code(ErrorCode.INTERNAL_SERVER_ERROR)
                    .message(ex.getMessage())
                    .build()))
            .build());
  }

  /**
   * Handles all uncaught exceptions as internal server errors.
   *
   * @param ex the exception caught from controller
   * @return 500 {@link ResponseEntity} with single {@link ErrorDetail}
   */
  @ExceptionHandler(Exception.class)
  public ResponseEntity<ErrorDto> handleGeneric(Exception ex) {
    log.error("Unexpected server error:", ex);
    return ResponseEntity
        .status(HttpStatus.INTERNAL_SERVER_ERROR)
        .body(ErrorDto.builder()
            .errors(Collections.singletonList(
                ErrorDetail.builder()
                    .code(ErrorCode.INTERNAL_SERVER_ERROR)
                    .message(ErrorMessages.UNEXPECTED_SERVER_ERROR.message())
                    .build()))
            .build());
  }

  /* ==================== Helper Methods ==================== */

  /**
   * Maps a Spring {@link FieldError} to a contract-safe {@link ErrorDetail}.
   *
   * @param fieldError the {@link FieldError} from Spring validation
   * @return {@link ErrorDetail} with mapped {@link ErrorCode}
   */
  private ErrorDetail mapFieldError(FieldError fieldError) {
    ErrorCode code = errorCodeMapper.map(fieldError.getCode());

    return ErrorDetail.builder()
        .field(fieldError.getField())
        .code(code)
        .message(fieldError.getDefaultMessage())
        .build();
  }
}