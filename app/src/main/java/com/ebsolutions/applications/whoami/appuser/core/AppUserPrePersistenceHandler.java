package com.ebsolutions.applications.whoami.appuser.core;

import java.time.LocalDateTime;
import java.util.UUID;
import org.springframework.data.relational.core.mapping.event.BeforeConvertCallback;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

@Component
public class AppUserPrePersistenceHandler implements BeforeConvertCallback<AppUser> {

  @Override
  @NonNull
  public AppUser onBeforeConvert(@NonNull AppUser entity) {
    // Ensure userId is set
    if (entity.getUserId() == null) {
      entity.setUserId(UUID.randomUUID());
    }

    // Set timestamps
    LocalDateTime now = LocalDateTime.now();
    if (entity.getCreatedAt() == null) {
      entity.setCreatedAt(now);
    }
    // Always update on insert or update
    entity.setUpdatedAt(now);

    return entity;
  }
}
