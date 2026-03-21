package com.ebsolutions.applications.whoami.core.system;

import com.ebsolutions.applications.whoami.config.ApiPaths;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ExceptionTestController extends SystemTestBaseController {

  @GetMapping(ApiPaths.EXCEPTION_TEST_PATH)
  @SuppressWarnings("java:S112") // Allow throwing generic Exception for testing purposes
  public ResponseEntity<String> postThrowException() throws Exception {

    throw new Exception("This is a test exception for testing global exception handling.");

  }
}