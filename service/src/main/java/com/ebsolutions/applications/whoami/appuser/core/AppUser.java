package com.ebsolutions.applications.whoami.appuser.core;

import com.ebsolutions.applications.whoami.core.persistence.PersistedEntity;
import java.time.LocalDateTime;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Getter
@Setter
@Builder
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Table("app_user")
public class AppUser implements PersistedEntity {
  @Id
  private Long internalId;
  private UUID externalId;
  private String emailAddress;
  private String firstName;
  private String lastName;
  private LocalDateTime createdAt;
  private LocalDateTime updatedAt;
}
