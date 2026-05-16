package com.ebsolutions.applications.whoami.acceptance.system;

import static org.assertj.core.api.Assertions.assertThat;

import com.ebsolutions.applications.whoami.acceptance.AcceptanceStepsContext;
import com.ebsolutions.applications.whoami.common.testfixture.ExpectedResponseValues;
import com.ebsolutions.applications.whoami.core.config.ApiPaths;
import io.cucumber.java.en.Given;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class ApplicationHealthEndpointStepsContext extends AcceptanceStepsContext {

  @Given("the application is running")
  public void theApplicationIsRunning() {

    ResponseEntity<String> response = restClient
        .get()
        .uri(ApiPaths.APPLICATION_HEALTH_PATH)
        .retrieve()
        .toEntity(String.class);

    assertThat(response.getStatusCode())
        .isEqualTo(HttpStatus.OK);

    assertThat(response.getBody())
        .contains(ExpectedResponseValues.EXPECTED_APPLICATION_HEALTH_STATUS_KEY);

    assertThat(response.getBody())
        .contains(ExpectedResponseValues.EXPECTED_APPLICATION_HEALTH_STATUS_VALUE);
  }
}
