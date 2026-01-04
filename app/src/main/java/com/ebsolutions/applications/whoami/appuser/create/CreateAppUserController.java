package com.ebsolutions.applications.whoami.appuser.create;

import com.ebsolutions.applications.whoami.appuser.core.AppUserBaseController;
import com.ebsolutions.applications.whoami.model.AppUserCreateRequest;
import com.ebsolutions.applications.whoami.model.AppUserResponse;
import java.net.URI;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class CreateAppUserController extends AppUserBaseController {
  private final CreateAppUserService createAppUserService;

  @PostMapping
  public ResponseEntity<AppUserResponse> postAppUser(
      @RequestBody AppUserCreateRequest appUserCreateRequest) {
    AppUserResponse appUserResponse = createAppUserService.createAppUser(appUserCreateRequest);
    return ResponseEntity
        .created(URI.create(REQUEST_MAPPING_BASE_PATH + "/" + appUserResponse.getUserId()))
        .body(appUserResponse);
  }
}
