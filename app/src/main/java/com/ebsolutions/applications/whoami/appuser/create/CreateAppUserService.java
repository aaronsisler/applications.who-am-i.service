package com.ebsolutions.applications.whoami.appuser.create;

import com.ebsolutions.applications.whoami.appuser.core.AppUser;
import com.ebsolutions.applications.whoami.appuser.core.AppUserMapper;
import com.ebsolutions.applications.whoami.appuser.core.AppUserRepository;
import com.ebsolutions.applications.whoami.model.AppUserCreateRequest;
import com.ebsolutions.applications.whoami.model.AppUserResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class CreateAppUserService {
  private final AppUserRepository repository;
  private final AppUserMapper mapper;

  public AppUserResponse createAppUser(AppUserCreateRequest request) {
    System.out.println(request);
    AppUser entity = mapper.toEntity(request);
    System.out.println(entity);
    AppUser saved = repository.save(entity);

    return mapper.toDto(saved);
  }
}
