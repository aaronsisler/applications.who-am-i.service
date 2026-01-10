package com.ebsolutions.applications.whoami.core;

import com.ebsolutions.applications.whoami.model.ErrorResponse;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.ConstraintViolationException;
import java.util.Collections;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingRequestHeaderException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.server.ResponseStatusException;

@Slf4j
@RestControllerAdvice
public class GlobalControllerExceptionHandler {

  /* ========== 401 – UNAUTHORIZED ========== */

  /**
   * This will be called when Authorization header is missing or invalid
   *
   * @param missingRequestHeaderException caught in controller
   * @return custom response with descriptive error messages
   */
  @ApiResponses({
      @ApiResponse(responseCode = "401",
          content = @Content(mediaType = "application/json",
              schema = @Schema(implementation = ErrorResponse.class)))
  })
  @ExceptionHandler(MissingRequestHeaderException.class)
  public ResponseEntity<ErrorResponse> handleMissingHeader(
      MissingRequestHeaderException missingRequestHeaderException) {

    return ResponseEntity
        .status(HttpStatus.UNAUTHORIZED)
        .body(ErrorResponse.builder()
            .messages(Collections.singletonList("Missing Authorization header"))
            .build());
  }

  /* ========== 403 – FORBIDDEN ========== */

  /**
   * This will be called when the authenticated client lacks permission
   *
   * @param exception caught in controller
   * @return custom response with descriptive error messages
   */
  @ApiResponses({
      @ApiResponse(responseCode = "403",
          content = @Content(mediaType = "application/json",
              schema = @Schema(implementation = ErrorResponse.class)))
  })
  @ExceptionHandler(ResponseStatusException.class)
  public ResponseEntity<ErrorResponse> handleForbidden(
      ResponseStatusException exception) {

    if (exception.getStatusCode() == HttpStatus.FORBIDDEN) {
      return ResponseEntity
          .status(HttpStatus.FORBIDDEN)
          .body(ErrorResponse.builder()
              .messages(Collections.singletonList(exception.getReason()))
              .build());
    }

    throw exception;   // delegate to other handlers
  }

  /* ========== 400 – BAD REQUESTS (INPUT / CONTRACT) ========== */

  /**
   * This will be called when a body field does not meet constraints
   *
   * @param methodArgumentNotValidException caught in controller
   * @return custom response with descriptive error messages
   */
  @ApiResponses({
      @ApiResponse(responseCode = "400",
          content = @Content(mediaType = "application/json",
              schema = @Schema(implementation = ErrorResponse.class)))
  })
  @ExceptionHandler(MethodArgumentNotValidException.class)
  public ResponseEntity<ErrorResponse> handleMethodArgumentNotValid(
      MethodArgumentNotValidException methodArgumentNotValidException) {

    //    List<String> messages = methodArgumentNotValidException.getBindingResult()
    //        .getAllErrors()
    //        .stream()
    //        .map(DefaultMessageSourceResolvable::getDefaultMessage)
    //        .toList();
    //
    //    return ResponseEntity.badRequest()
    //        .body(ErrorResponse.builder()
    //            .messages(messages)
    //            .build());

    List<String> messages = methodArgumentNotValidException.getBindingResult()
        .getAllErrors()
        .stream()
        .map(error -> {
          if (error instanceof FieldError fieldError) {
            return fieldError.getField() + " " + fieldError.getDefaultMessage();
          }
          return error.getDefaultMessage();
        })
        .toList();

    return ResponseEntity.badRequest()
        .body(
            ErrorResponse.builder()
                .messages(messages)
                .build()
        );
  }

  /**
   * This will be called when query or contract constraints are violated
   *
   * @param constraintViolationException caught in controller
   * @return custom response with descriptive error messages
   */
  @ApiResponses({
      @ApiResponse(responseCode = "400",
          content = @Content(mediaType = "application/json",
              schema = @Schema(implementation = ErrorResponse.class)))
  })
  @ExceptionHandler(ConstraintViolationException.class)
  public ResponseEntity<ErrorResponse> handleConstraintViolation(
      ConstraintViolationException constraintViolationException) {

    List<String> messages = constraintViolationException.getConstraintViolations()
        .stream()
        .map(v -> v.getPropertyPath() + " " + v.getMessage())
        .toList();

    return ResponseEntity
        .badRequest()
        .body(ErrorResponse.builder()
            .messages(messages)
            .build());
  }

  /* ========== 405 – METHOD NOT ALLOWED ========== */

  /**
   * This will be called when an HTTP method is not allowed
   *
   * @param httpRequestMethodNotSupportedException caught in controller
   * @return custom response with descriptive error messages
   */
  @ApiResponses({
      @ApiResponse(responseCode = "405",
          content = @Content(mediaType = "application/json",
              schema = @Schema(implementation = ErrorResponse.class)))
  })
  @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
  public ResponseEntity<ErrorResponse> handleMethodNotSupported(
      HttpRequestMethodNotSupportedException httpRequestMethodNotSupportedException) {

    return ResponseEntity
        .status(HttpStatus.METHOD_NOT_ALLOWED)
        .body(ErrorResponse.builder()
            .messages(Collections.singletonList("HTTP method not allowed"))
            .build());
  }

  /* ========== 409 – CONFLICT ========== */

  /**
   * This will be called when database integrity constraints are violated
   *
   * @param dataIntegrityViolationException caught in controller
   * @return custom response with descriptive error messages
   */
  @ApiResponses({
      @ApiResponse(responseCode = "409",
          content = @Content(mediaType = "application/json",
              schema = @Schema(implementation = ErrorResponse.class)))
  })
  @ExceptionHandler(DataIntegrityViolationException.class)
  public ResponseEntity<ErrorResponse> handleDataIntegrity(
      DataIntegrityViolationException dataIntegrityViolationException) {

    return ResponseEntity
        .status(HttpStatus.CONFLICT)
        .body(ErrorResponse.builder()
            .messages(Collections.singletonList("emailAddress already exists"))
            .build());
  }

  /* ========== 415 – UNSUPPORTED MEDIA TYPE ========== */

  /**
   * This will be called when unsupported Content-Type is supplied
   *
   * @param httpMediaTypeNotSupportedException caught in controller
   * @return custom response with descriptive error messages
   */
  @ApiResponses({
      @ApiResponse(responseCode = "415",
          content = @Content(mediaType = "application/json",
              schema = @Schema(implementation = ErrorResponse.class)))
  })
  @ExceptionHandler(HttpMediaTypeNotSupportedException.class)
  public ResponseEntity<ErrorResponse> handleUnsupportedMedia(
      HttpMediaTypeNotSupportedException httpMediaTypeNotSupportedException) {

    return ResponseEntity
        .status(HttpStatus.UNSUPPORTED_MEDIA_TYPE)
        .body(ErrorResponse.builder()
            .messages(Collections.singletonList("Unsupported Content-Type"))
            .build());
  }

  /* ========== 503 – SERVICE UNAVAILABLE ========== */

  /**
   * This will be called when the data store cannot be reached
   *
   * @param resourceAccessException caught in controller
   * @return custom response with descriptive error messages
   */
  @ApiResponses({
      @ApiResponse(responseCode = "503",
          content = @Content(mediaType = "application/json",
              schema = @Schema(implementation = ErrorResponse.class)))
  })
  @ExceptionHandler(ResourceAccessException.class)
  public ResponseEntity<ErrorResponse> handleDataStoreUnavailable(
      ResourceAccessException resourceAccessException) {

    return ResponseEntity
        .status(HttpStatus.SERVICE_UNAVAILABLE)
        .body(ErrorResponse.builder()
            .messages(Collections.singletonList("Data store unavailable"))
            .build());
  }

  /* ========== 500 – INTERNAL SERVER ERROR ========== */

  /**
   * This will handle any unexpected exceptions
   *
   * @param exception caught in controller
   * @return custom response with descriptive error messages
   */
  @ApiResponses({
      @ApiResponse(responseCode = "500",
          content = @Content(mediaType = "application/json",
              schema = @Schema(implementation = ErrorResponse.class)))
  })
  @ExceptionHandler(Exception.class)
  public ResponseEntity<ErrorResponse> handleGeneric(Exception exception) {
    log.error("Server Error: {0}", exception);

    return ResponseEntity
        .status(HttpStatus.INTERNAL_SERVER_ERROR)
        .body(ErrorResponse.builder()
            .messages(Collections.singletonList("Unexpected server error"))
            .build());
  }
}
