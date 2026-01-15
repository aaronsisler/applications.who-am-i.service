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
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingRequestHeaderException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
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
      @ApiResponse(responseCode = HttpResponseCodes.UNAUTHORIZED,
          content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
              schema = @Schema(implementation = ErrorResponse.class)))
  })
  @ExceptionHandler(MissingRequestHeaderException.class)
  public ResponseEntity<ErrorResponse> handleMissingHeader(
      MissingRequestHeaderException missingRequestHeaderException) {

    return ResponseEntity
        .status(HttpStatus.UNAUTHORIZED)
        .body(ErrorResponse.builder()
            .messages(Collections.singletonList(ErrorMessages.MISSING_AUTH_N_HEADER))
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
      @ApiResponse(responseCode = HttpResponseCodes.FORBIDDEN,
          content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
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

    // Delegate to other handlers
    throw exception;
  }

  /* ========== 400 – BAD REQUESTS (INPUT / CONTRACT) ========== */

  /**
   * This will be called when a body field does not meet constraints
   *
   * @param methodArgumentNotValidException caught in controller
   * @return custom response with descriptive error messages
   */
  @ApiResponses({
      @ApiResponse(responseCode = HttpResponseCodes.BAD_REQUEST,
          content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
              schema = @Schema(implementation = ErrorResponse.class)))
  })
  @ExceptionHandler(MethodArgumentNotValidException.class)
  public ResponseEntity<ErrorResponse> handleMethodArgumentNotValid(
      MethodArgumentNotValidException methodArgumentNotValidException) {
    System.out.println("MethodArgumentNotValidException");

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
      @ApiResponse(responseCode = HttpResponseCodes.BAD_REQUEST,
          content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
              schema = @Schema(implementation = ErrorResponse.class)))
  })
  @ExceptionHandler(ConstraintViolationException.class)
  public ResponseEntity<ErrorResponse> handleConstraintViolation(
      ConstraintViolationException constraintViolationException) {
    System.out.println("ConstraintViolationException");

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

  /**
   * This will be called when the body of http request is not parseable
   *
   * @param httpMessageNotReadableException caught in controller
   * @return custom response with descriptive error messages
   */
  @ApiResponses({
      @ApiResponse(responseCode = HttpResponseCodes.BAD_REQUEST,
          content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
              schema = @Schema(implementation = ErrorResponse.class)))
  })
  @ExceptionHandler(HttpMessageNotReadableException.class)
  public ResponseEntity<ErrorResponse> handleMessageNotReadable(
      HttpMessageNotReadableException httpMessageNotReadableException) {
    System.out.println("HttpMessageNotReadableException");


    return ResponseEntity
        .badRequest()
        .body(ErrorResponse.builder()
            .messages(Collections.singletonList(ErrorMessages.MESSAGE_NOT_READABLE))
            .build());
  }

  /**
   * This will be called when any data format is invalid and thrown from a service
   *
   * @param invalidDataFormatException caught in controller
   * @return custom response with descriptive error messages
   */
  @ApiResponses({
      @ApiResponse(responseCode = HttpResponseCodes.BAD_REQUEST,
          content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
              schema = @Schema(implementation = ErrorResponse.class)))
  })
  @ExceptionHandler(InvalidDataFormatException.class)
  public ResponseEntity<ErrorResponse> handleInvalidDataFormatException(
      InvalidDataFormatException invalidDataFormatException) {
    System.out.println("InvalidDataFormatException");
    return ResponseEntity
        .badRequest()
        .body(ErrorResponse.builder()
            .messages(Collections.singletonList(invalidDataFormatException.getMessage()))
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
      @ApiResponse(responseCode = HttpResponseCodes.METHOD_NOT_ALLOWED,
          content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
              schema = @Schema(implementation = ErrorResponse.class)))
  })
  @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
  public ResponseEntity<ErrorResponse> handleMethodNotSupported(
      HttpRequestMethodNotSupportedException httpRequestMethodNotSupportedException) {

    return ResponseEntity
        .status(HttpStatus.METHOD_NOT_ALLOWED)
        .body(ErrorResponse.builder()
            .messages(Collections.singletonList(ErrorMessages.HTTP_METHOD_NOT_ALLOWED))
            .build());
  }

  /* ========== 409 – CONFLICT ========== */

  /**
   * This will be called when database duplication constraints are violated
   *
   * @param duplicateDataException caught in controller
   * @return custom response with descriptive error messages
   */
  @ApiResponses({
      @ApiResponse(responseCode = HttpResponseCodes.CONFLICT,
          content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
              schema = @Schema(implementation = ErrorResponse.class)))
  })
  @ExceptionHandler(DuplicateDataException.class)
  public ResponseEntity<ErrorResponse> handleDuplicateData(
      DuplicateDataException duplicateDataException) {

    return ResponseEntity
        .status(HttpStatus.CONFLICT)
        .body(ErrorResponse.builder()
            .messages(Collections.singletonList(duplicateDataException.getMessage()))
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
      @ApiResponse(responseCode = HttpResponseCodes.UNSUPPORTED_MEDIA_TYPE,
          content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
              schema = @Schema(implementation = ErrorResponse.class)))
  })
  @ExceptionHandler(HttpMediaTypeNotSupportedException.class)
  public ResponseEntity<ErrorResponse> handleUnsupportedMedia(
      HttpMediaTypeNotSupportedException httpMediaTypeNotSupportedException) {

    return ResponseEntity
        .status(HttpStatus.UNSUPPORTED_MEDIA_TYPE)
        .body(ErrorResponse.builder()
            .messages(Collections.singletonList(ErrorMessages.UNSUPPORTED_CONTENT_TYPE))
            .build());
  }

  /* ========== 503 – SERVICE UNAVAILABLE ========== */

  /**
   * This will be called when the data store cannot be reached
   *
   * @param dataStoreException caught in controller
   * @return custom response with descriptive error messages
   */
  @ApiResponses({
      @ApiResponse(responseCode = HttpResponseCodes.SERVICE_UNAVAILABLE,
          content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
              schema = @Schema(implementation = ErrorResponse.class)))
  })
  @ExceptionHandler(DataStoreException.class)
  public ResponseEntity<ErrorResponse> handleDataStoreUnavailable(
      DataStoreException dataStoreException) {

    return ResponseEntity
        .status(HttpStatus.SERVICE_UNAVAILABLE)
        .body(ErrorResponse.builder()
            .messages(Collections.singletonList(dataStoreException.getMessage()))
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
      @ApiResponse(responseCode = HttpResponseCodes.INTERNAL_SERVER_ERROR,
          content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
              schema = @Schema(implementation = ErrorResponse.class)))
  })
  @ExceptionHandler(Exception.class)
  public ResponseEntity<ErrorResponse> handleGeneric(Exception exception) {
    log.error("Server Error:", exception);

    return ResponseEntity
        .status(HttpStatus.INTERNAL_SERVER_ERROR)
        .body(ErrorResponse.builder()
            .messages(Collections.singletonList(ErrorMessages.UNEXPECTED_SERVER_ERROR))
            .build());
  }
}
