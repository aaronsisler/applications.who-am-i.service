package com.ebsolutions.applications.whoami.common.nonfunctional;

import static org.assertj.core.api.Assertions.assertThat;

import com.ebsolutions.applications.whoami.common.HttpStepsContext;
import com.ebsolutions.applications.whoami.common.testfixture.ExpectedResponseValues;
import com.ebsolutions.applications.whoami.common.testfixture.ScenarioResponse;
import com.ebsolutions.applications.whoami.core.config.ApiPaths;
import io.cucumber.java.en.Given;
import org.springframework.http.HttpStatus;


public class ApplicationRunningStepsContext extends HttpStepsContext {

  @Given("the application is running")
  public void theApplicationIsRunning() {

    ScenarioResponse response = restApiClient.get(ApiPaths.APPLICATION_HEALTH_PATH);

    assertThat(response.statusCode())
        .isEqualTo(HttpStatus.OK.value());

    assertThat(response.body())
        .contains(ExpectedResponseValues.EXPECTED_APPLICATION_HEALTH_STATUS_KEY);

    assertThat(response.body())
        .contains(ExpectedResponseValues.EXPECTED_APPLICATION_HEALTH_STATUS_VALUE);
  }
}
