package com.ebsolutions.applications.whoami.acceptance.appuser.create;

import com.ebsolutions.applications.whoami.acceptance.AcceptanceStepsContext;
import com.ebsolutions.applications.whoami.core.config.ApiPaths;
import io.cucumber.java.en.When;

public class CreateAppUserWhenStepsContext extends AcceptanceStepsContext {

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
