package com.ebsolutions.applications.whoami.core;

import java.time.LocalDateTime;
import java.util.UUID;

public interface PersistedEntity {
  /**
   * The internal ID is a database-generated identifier that is not exposed to clients.
   * It is used for internal references and should not be used in client-facing APIs.
   */
  Long getInternalId();

  void setInternalId(Long internalId);

  /**
   * The external ID (UUID) is the identifier that is exposed to clients.
   * It is used for external references and should not be used in database-generated contexts.
   */
  UUID getExternalId();

  void setExternalId(UUID externalId);

  LocalDateTime getCreatedAt();

  void setCreatedAt(LocalDateTime createdAt);

  LocalDateTime getUpdatedAt();

  void setUpdatedAt(LocalDateTime updatedAt);
}
