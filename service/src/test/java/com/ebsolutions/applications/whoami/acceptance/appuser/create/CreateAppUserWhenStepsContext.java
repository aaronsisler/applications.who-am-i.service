package com.ebsolutions.applications.whoami.acceptance.appuser.create;

import com.ebsolutions.applications.whoami.common.CommonContext;
import com.ebsolutions.applications.whoami.common.http.RestApiClient;
import com.ebsolutions.applications.whoami.common.testfixture.ScenarioContext;
import com.ebsolutions.applications.whoami.core.config.ApiPaths;
import io.cucumber.java.en.When;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class CreateAppUserWhenStepsContext extends CommonContext {

  private final RestApiClient restApiClient;
  private final ScenarioContext scenarioContext;


  @When("the client submits the create-user request")
  public void theClientSubmitsTheCreateUserRequest() {

    this.scenarioContext.response = restApiClient
        .post(
            ApiPaths.APP_USERS_PATH,
            this.scenarioContext.requestContentType,
            this.scenarioContext.requestPayload
        );
  }
}
