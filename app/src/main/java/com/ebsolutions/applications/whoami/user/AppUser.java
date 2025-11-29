package com.ebsolutions.applications.whoami.user;

import java.time.LocalDateTime;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Table("app_user")
public class AppUser {
  @Id
  private Long userId;
  private String email;
  private String firstName;
  private String lastName;
  private LocalDateTime createdAt;
  private LocalDateTime updateAt;
  private String createdBy;
  private String updateBy;
}
