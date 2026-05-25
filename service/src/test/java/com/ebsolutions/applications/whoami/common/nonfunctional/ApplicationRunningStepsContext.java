package com.ebsolutions.applications.whoami.common.nonfunctional;


import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import com.ebsolutions.applications.whoami.common.CommonContext;
import com.ebsolutions.applications.whoami.common.http.RestApiClient;
import com.ebsolutions.applications.whoami.common.testfixture.ExpectedResponseValues;
import com.ebsolutions.applications.whoami.common.testfixture.ScenarioResponse;
import com.ebsolutions.applications.whoami.core.config.ApiPaths;
import io.cucumber.java.en.Given;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@RequiredArgsConstructor
public class ApplicationRunningStepsContext extends CommonContext {

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
