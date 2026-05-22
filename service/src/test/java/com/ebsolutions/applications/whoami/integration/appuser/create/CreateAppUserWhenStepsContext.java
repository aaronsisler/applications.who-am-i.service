package com.ebsolutions.applications.whoami.integration.appuser.create;

import com.ebsolutions.applications.whoami.core.config.ApiPaths;
import com.ebsolutions.applications.whoami.integration.IntegrationStepsContext;
import io.cucumber.java.en.When;

public class CreateAppUserWhenStepsContext extends IntegrationStepsContext {
  @When("the client submits the create-user request")
  public void theClientSubmitsTheCreateUserRequest() {

    this.scenarioContext.response =
        this.restApiClient.post(
            ApiPaths.APP_USERS_PATH,
            this.scenarioContext.requestContentType,
            this.scenarioContext.requestPayload
        );

  }

  @When("the client submits both of the create-user requests")
  public void theClientSubmitsBothOfTheCreateUserRequests() {

    integrationScenarioContext.listOfRequestContents.forEach(requestContent -> {
      this.scenarioContext.response =
          this.restApiClient.post(
              ApiPaths.APP_USERS_PATH,
              this.scenarioContext.requestContentType,
              requestContent
          );
    });

  }
}
