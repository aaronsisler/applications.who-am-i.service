package com.ebsolutions.applications.whoami.appuser.core;

import com.ebsolutions.applications.whoami.core.ErrorMessages;
import com.ebsolutions.applications.whoami.core.InvalidDataFormatException;
import java.util.regex.Pattern;
import org.jspecify.annotations.NonNull;
import org.springframework.stereotype.Component;

@Component
public class EmailAddressValidator {
  private static final Pattern STRICT_EMAIL =
      Pattern.compile("^[^@\\s]+@[^@\\s]+\\.[^@\\s]+$");

  /**
   * Validates the given raw email address.
   *
   * @param rawEmailAddress The raw email address to validate.
   * @throws InvalidDataFormatException if the email address is invalid.
   */
  public void validate(@NonNull String rawEmailAddress) throws InvalidDataFormatException {
    if (!STRICT_EMAIL.matcher(rawEmailAddress).matches()) {
      throw new InvalidDataFormatException(ErrorMessages.EMAIL_FORMAT_INVALID.message());
    }
  }
}
