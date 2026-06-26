package com.ebsolutions.applications.whoami.appuser.create;

import com.ebsolutions.applications.whoami.core.config.ApiPaths;
import com.ebsolutions.applications.whoami.testfixture.CommonContext;
import com.ebsolutions.applications.whoami.testfixture.ScenarioContext;
import com.ebsolutions.applications.whoami.testfixture.http.RestApiClient;
import com.ebsolutions.applications.whoami.testfixture.integration.IntegrationScenarioContext;
import io.cucumber.java.en.When;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class CreateAppUserWhenStepsContext extends CommonContext {

  private final IntegrationScenarioContext integrationScenarioContext;
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
