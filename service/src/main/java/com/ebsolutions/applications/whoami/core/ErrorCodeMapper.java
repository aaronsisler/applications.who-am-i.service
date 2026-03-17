package com.ebsolutions.applications.whoami.core;

import com.ebsolutions.applications.whoami.dto.ErrorCode;
import org.springframework.stereotype.Component;
import org.springframework.validation.FieldError;

@Component
public class ErrorCodeMapper {
  /**
   * Maps Spring validation codes to {@link ErrorCode} enum.
   *
   * @param value value from {@link FieldError} (could be null)
   * @return corresponding {@link ErrorCode} from contract
   */
  public ErrorCode map(String value) {
    if (value == null) {
      return ErrorCode.INTERNAL_SERVER_ERROR;
    }

    return switch (value) {
      case "NotNull" -> ErrorCode.NOT_NULL;
      case "NotBlank" -> ErrorCode.NOT_BLANK;
      case "Size" -> ErrorCode.SIZE;
      case "Email" -> ErrorCode.EMAIL_FORMAT_INVALID;
      default -> ErrorCode.INTERNAL_SERVER_ERROR;
    };
  }
}
