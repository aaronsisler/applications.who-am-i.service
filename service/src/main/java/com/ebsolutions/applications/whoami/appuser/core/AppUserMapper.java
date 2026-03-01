package com.ebsolutions.applications.whoami.appuser.core;

import com.ebsolutions.applications.whoami.dto.AppUserCreate;
import com.ebsolutions.applications.whoami.dto.AppUserDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface AppUserMapper {

  /**
   * Maps an AppUserCreate (STO) to an AppUser entity.
   */
  AppUser toEntity(AppUserCreate appUserCreate);

  /**
   * Maps an AppUser entity to an AppUserDto (DTO).
   */
  AppUserDto toDto(AppUser entity);
}
