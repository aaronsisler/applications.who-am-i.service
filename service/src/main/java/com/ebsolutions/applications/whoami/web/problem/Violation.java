package com.ebsolutions.applications.whoami.web.problem;

import static java.util.stream.Collectors.joining;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.constraints.*;
import java.util.Arrays;
import java.util.Collection;
import org.jspecify.annotations.Nullable;
import org.springframework.context.MessageSourceResolvable;
import org.springframework.core.MethodParameter;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;

/** One entry in a Problem's "violations" member, as documented in common/problems.yaml. */
record Violation(String field, String code, String message) {

  static Violation of(FieldError error) {
    return new Violation(error.getField(), codeFor(error), error.getDefaultMessage());
  }

  static Violation of(MethodParameter parameter, MessageSourceResolvable error) {
    return new Violation(parameter.getParameterName(), codeFor(error), error.getDefaultMessage());
  }

  /** A query parameter or path variable whose value can't be converted to its type (ADR 0021). */
  static Violation invalidValue(String field, @Nullable Class<?> type) {
    return new Violation(field, "INVALID_FORMAT", messageFor(type));
  }

  /** Describes the problem from the client's side, not the validation technology (ADR 0007). */
  private static String codeFor(MessageSourceResolvable error) {
    if (!(error instanceof ObjectError objectError)
        || !objectError.contains(ConstraintViolation.class)) {
      return "INVALID";
    }
    ConstraintViolation<?> violation = objectError.unwrap(ConstraintViolation.class);
    return switch (violation.getConstraintDescriptor().getAnnotation()) {
      case NotNull _, NotBlank _, NotEmpty _ -> "REQUIRED";
      case Size size -> length(violation.getInvalidValue()) > size.max() ? "TOO_LONG" : "TOO_SHORT";
      case Email _, Pattern _ -> "INVALID_FORMAT";
      case Min _, Max _, DecimalMin _, DecimalMax _, Positive _, PositiveOrZero _ -> "OUT_OF_RANGE";
      default -> "INVALID";
    };
  }

  /** Generated enums' toString() returns the contract value, so clients see "-lastName". */
  private static String messageFor(@Nullable Class<?> type) {
    if (type == null) {
      return "has an invalid format";
    }
    if (type.isEnum()) {
      return "must be one of: "
          + Arrays.stream(type.getEnumConstants()).map(String::valueOf).collect(joining(", "));
    }
    return "must be a valid " + type.getSimpleName();
  }

  private static int length(Object value) {
    return switch (value) {
      case CharSequence text -> text.length();
      case Collection<?> items -> items.size();
      case null, default -> 0;
    };
  }
}
