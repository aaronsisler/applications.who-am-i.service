package com.ebsolutions.applications.whoami.appuser.create;

import com.ebsolutions.applications.whoami.appuser.core.AppUser;
import com.ebsolutions.applications.whoami.appuser.core.AppUserMapper;
import com.ebsolutions.applications.whoami.appuser.core.AppUserRepository;
import com.ebsolutions.applications.whoami.appuser.core.EmailAddressValidator;
import com.ebsolutions.applications.whoami.core.DataStoreException;
import com.ebsolutions.applications.whoami.core.DuplicateDataException;
import com.ebsolutions.applications.whoami.core.ErrorMessages;
import com.ebsolutions.applications.whoami.model.AppUserCreateRequest;
import com.ebsolutions.applications.whoami.model.AppUserResponse;
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
  private final EmailAddressValidator emailAddressValidator;

  public AppUserResponse createAppUser(AppUserCreateRequest request) {

    System.out.println("HERE UP");
    emailAddressValidator.validate(request.getEmailAddress());
    System.out.println("HERE DOWN");

    try {
      AppUser entity = mapper.toEntity(request);
      AppUser saved = repository.save(entity);

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
