package com.ebsolutions.applications.whoami.appuser.create;

import com.ebsolutions.applications.whoami.appuser.core.AppUserBaseController;
import com.ebsolutions.applications.whoami.config.ApiPaths;
import com.ebsolutions.applications.whoami.dto.AppUserCreate;
import com.ebsolutions.applications.whoami.dto.AppUserDto;
import jakarta.validation.Valid;
import java.net.URI;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@Slf4j
public class CreateAppUserController extends AppUserBaseController {
  private final CreateAppUserService createAppUserService;

  @PostMapping
  public ResponseEntity<AppUserDto> postAppUser(
      @Valid @RequestBody AppUserCreate appUserCreate) {

    AppUserDto appUserResponse = createAppUserService.createAppUser(appUserCreate);

    return ResponseEntity
        .created(URI.create(ApiPaths.APP_USERS_PATH + "/" + appUserResponse.getExternalId()))
        .body(appUserResponse);
  }
}
