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
import java.sql.SQLTransientException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DuplicateKeyException;
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
      log.error("CREATE SERVICE HIT 1");
      AppUser saved = repository.save(preProcessedEntity);
      log.error("CREATE SERVICE HIT 2");

      return mapper.toDto(saved);
    } catch (Exception ex) {
      throw translate(ex);
    }
  }

  private RuntimeException translate(Exception ex) {
    if (ex.getCause() instanceof DuplicateKeyException) {
      return new DuplicateDataException(
          ErrorMessages.EMAIL_ALREADY_EXISTS.message(),
          ex
      );
    }

    if (ex.getCause() instanceof SQLTransientException) {
      return new DataStoreException(ErrorMessages.UNEXPECTED_SERVER_ERROR.message(), ex);
    }

    if (ex.getCause() instanceof RuntimeException) {
      return new DataStoreException(ErrorMessages.UNEXPECTED_SERVER_ERROR.message(), ex);
    }

    return new DataStoreException(ErrorMessages.APP_USER_NOT_SAVED.message(), ex);
  }
}
