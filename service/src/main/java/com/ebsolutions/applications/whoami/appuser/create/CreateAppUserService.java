package com.ebsolutions.applications.whoami.appuser.create;

import com.ebsolutions.applications.whoami.appuser.core.AppUser;
import com.ebsolutions.applications.whoami.appuser.core.AppUserMapper;
import com.ebsolutions.applications.whoami.appuser.core.AppUserRepository;
import com.ebsolutions.applications.whoami.core.ErrorMessages;
import com.ebsolutions.applications.whoami.core.exception.DataStoreException;
import com.ebsolutions.applications.whoami.core.exception.DuplicateDataException;
import com.ebsolutions.applications.whoami.core.persistence.PrePersistenceHandler;
import com.ebsolutions.applications.whoami.dto.AppUserCreate;
import com.ebsolutions.applications.whoami.dto.AppUserDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.data.relational.core.conversion.DbActionExecutionException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class CreateAppUserService {
  private final AppUserRepository repository;
  private final AppUserMapper mapper;
  private final PrePersistenceHandler<AppUser> appUserPrePersistenceHandler;

  public AppUserDto createAppUser(AppUserCreate appUserCreate) {

    try {
      AppUser entity = mapper.toEntity(appUserCreate);
      AppUser preProcessedEntity = appUserPrePersistenceHandler.onBeforePersist(entity);
      AppUser saved = repository.save(preProcessedEntity);

      return mapper.toDto(saved);
    } catch (DbActionExecutionException ex) {
      throw translate(ex);
    }
  }

  private RuntimeException translate(DbActionExecutionException ex) {
    if (ex.getCause() instanceof DuplicateKeyException) {
      return new DuplicateDataException(
          ErrorMessages.EMAIL_ALREADY_EXISTS.message(),
          ex
      );
    }
    return new DataStoreException(ErrorMessages.APP_USER_NOT_SAVED.message(), ex);
  }
}
