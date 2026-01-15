package com.ebsolutions.applications.whoami.appuser.core;

import com.ebsolutions.applications.whoami.model.AppUserCreateRequest;
import com.ebsolutions.applications.whoami.model.AppUserResponse;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface AppUserMapper {

  /**
   * Maps an AppUserCreateRequest (STO) to an AppUser entity.
   */
  AppUser toEntity(AppUserCreateRequest sto);

  /**
   * Maps an AppUser entity to an AppUserResponse (DTO).
   */
  AppUserResponse toDto(AppUser entity);
}
