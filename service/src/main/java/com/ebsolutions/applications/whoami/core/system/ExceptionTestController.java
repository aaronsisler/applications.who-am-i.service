package com.ebsolutions.applications.whoami.core.system;

import com.ebsolutions.applications.whoami.config.ApiPaths;
import com.ebsolutions.applications.whoami.dto.AppUserDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ExceptionTestController extends SystemTestBaseController {

  @GetMapping(ApiPaths.EXCEPTION_TEST_PATH)
  public ResponseEntity<AppUserDto> postThrowException() throws Exception {

    throw new Exception("This is a test exception for testing global exception handling.");

  }
}