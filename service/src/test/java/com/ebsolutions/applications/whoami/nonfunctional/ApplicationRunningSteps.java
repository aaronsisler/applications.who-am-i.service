package com.ebsolutions.applications.whoami.nonfunctional;


import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import com.ebsolutions.applications.whoami.core.config.ApiPaths;
import com.ebsolutions.applications.whoami.testfixture.context.BaseSteps;
import com.ebsolutions.applications.whoami.testfixture.http.ExpectedResponseValues;
import com.ebsolutions.applications.whoami.testfixture.http.RestApiClient;
import com.ebsolutions.applications.whoami.testfixture.http.ScenarioResponse;
import io.cucumber.java.en.Given;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@RequiredArgsConstructor
public class ApplicationRunningSteps extends BaseSteps {

  private final RestApiClient restApiClient;

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
