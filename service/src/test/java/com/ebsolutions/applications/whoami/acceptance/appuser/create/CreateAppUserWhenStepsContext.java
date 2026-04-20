package com.ebsolutions.applications.whoami.acceptance.appuser.create;

import com.ebsolutions.applications.whoami.acceptance.AcceptanceStepsContext;
import com.ebsolutions.applications.whoami.core.config.ApiPaths;
import io.cucumber.java.en.When;

public class CreateAppUserWhenStepsContext extends AcceptanceStepsContext {

  @When("the client submits the create-user request")
  public void theClientSubmitsTheCreateUserRequest() {

    System.out.println("RAWR");
    System.out.println(this.acceptanceScenarioContext.requestContentType);
    System.out.println(this.acceptanceScenarioContext.requestPayload);

    this.acceptanceScenarioContext.response = restClient
        .post()
        .uri(ApiPaths.APP_USERS_PATH)
        .contentType(this.acceptanceScenarioContext.requestContentType)
        .body(this.acceptanceScenarioContext.requestPayload)
        .retrieve()
        .toEntity(String.class);
  }
}
