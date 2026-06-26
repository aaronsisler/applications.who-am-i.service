package com.ebsolutions.applications.whoami.core.system;

import com.ebsolutions.applications.whoami.core.config.ApiPaths;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ExceptionTestController extends SystemTestBaseController {

  @GetMapping(ApiPaths.EXCEPTION_TEST_PATH)
  @SuppressWarnings("java:S112") // Allow throwing generic Exception for testing purposes
  public ResponseEntity<String> postThrowException() throws Exception {
    System.out.println("Exception endpoint hit on thread " + Thread.currentThread().getName());

    throw new Exception("This is a test exception for testing global exception handling.");

  }
}