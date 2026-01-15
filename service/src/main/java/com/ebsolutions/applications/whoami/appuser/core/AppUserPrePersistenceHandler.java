package com.ebsolutions.applications.whoami.appuser.core;

import com.ebsolutions.applications.whoami.core.LocalDateTimeGenerator;
import com.ebsolutions.applications.whoami.core.UuidGenerator;
import java.time.LocalDateTime;
import lombok.RequiredArgsConstructor;
import org.springframework.data.relational.core.mapping.event.BeforeConvertCallback;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AppUserPrePersistenceHandler implements BeforeConvertCallback<AppUser> {
  private final LocalDateTimeGenerator localDateTimeGenerator;
  private final UuidGenerator uuidGenerator;

  @Override
  @NonNull
  public AppUser onBeforeConvert(@NonNull AppUser entity) {
    // Ensure userId is set
    if (entity.getUserId() == null) {
      entity.setUserId(uuidGenerator.randomUuid());
    }

    // Set timestamps
    LocalDateTime now = localDateTimeGenerator.now();

    if (entity.getCreatedAt() == null) {
      entity.setCreatedAt(now);
    }
    // Always update on insert or update
    entity.setUpdatedAt(now);

    return entity;
  }
}
