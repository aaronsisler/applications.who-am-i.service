package com.ebsolutions.applications.whoami.core.persistence;

import com.ebsolutions.applications.whoami.core.generator.InstantGenerator;
import com.ebsolutions.applications.whoami.core.generator.UuidGenerator;
import java.time.Instant;
import lombok.RequiredArgsConstructor;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PrePersistenceHandler<T extends PersistedEntity> {
  private final UuidGenerator uuidGenerator;
  private final InstantGenerator instantGenerator;

  public T onBeforePersist(@NonNull T entity) {
    // Ensure externalId is set
    if (entity.getExternalId() == null) {
      entity.setExternalId(uuidGenerator.generate());
    }

    // Set timestamps
    Instant now = instantGenerator.now();

    if (entity.getCreatedAt() == null) {
      entity.setCreatedAt(now);
    }

    // Always update on insert or update
    entity.setUpdatedAt(now);

    return entity;
  }
}
