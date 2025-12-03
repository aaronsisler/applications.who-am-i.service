package com.ebsolutions.applications.whoami.appuser.core;

import java.time.LocalDateTime;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Table("app_user")
public class AppUser {
  @Id
  private Long id;
  private UUID userId;
  private String emailAddress;
  private String firstName;
  private String lastName;
  private LocalDateTime createdAt;
  private LocalDateTime updatedAt;
}
