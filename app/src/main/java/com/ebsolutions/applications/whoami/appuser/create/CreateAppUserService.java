package com.ebsolutions.applications.whoami.appuser.create;

import com.ebsolutions.applications.whoami.appuser.core.AppUser;
import com.ebsolutions.applications.whoami.appuser.core.AppUserMapper;
import com.ebsolutions.applications.whoami.appuser.core.AppUserRepository;
import com.ebsolutions.applications.whoami.core.DataStoreException;
import com.ebsolutions.applications.whoami.model.AppUserCreateRequest;
import com.ebsolutions.applications.whoami.model.AppUserResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CreateAppUserService {
  private final AppUserRepository repository;
  private final AppUserMapper mapper;

  public AppUserResponse createAppUser(AppUserCreateRequest request) {
    try {
      AppUser entity = mapper.toEntity(request);
      AppUser saved = repository.save(entity);

      return mapper.toDto(saved);
    } catch (DataIntegrityViolationException dataIntegrityViolationException) {
      throw dataIntegrityViolationException;
    } catch (Exception exception) {
      throw new DataStoreException("App user cannot be saved");
    }
  }
}
