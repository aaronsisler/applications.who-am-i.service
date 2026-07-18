package com.ebsolutions.applications.whoami.appuser.create;

import com.ebsolutions.applications.whoami.core.config.ApiPaths;
import com.ebsolutions.applications.whoami.testfixture.context.BaseSteps;
import com.ebsolutions.applications.whoami.testfixture.context.ScenarioContext;
import com.ebsolutions.applications.whoami.testfixture.http.RestApiClient;
import io.cucumber.java.en.When;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class CreateAppUserWhenSteps extends BaseSteps {

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
