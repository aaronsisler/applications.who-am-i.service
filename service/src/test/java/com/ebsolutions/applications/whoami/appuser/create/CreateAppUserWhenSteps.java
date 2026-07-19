package com.ebsolutions.applications.whoami.appuser.create;

import com.ebsolutions.applications.whoami.core.config.ApiPaths;
import com.ebsolutions.applications.whoami.testfixture.context.BaseSteps;
import com.ebsolutions.applications.whoami.testfixture.context.ScenarioContext;
import com.ebsolutions.applications.whoami.testfixture.http.RestApiClient;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.cucumber.java.en.When;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class CreateAppUserWhenSteps extends BaseSteps {

  private final RestApiClient restApiClient;
  private final ScenarioContext scenarioContext;
  private final ObjectMapper objectMapper;

  @When("the client submits the create-user request")
  public void theClientSubmitsTheCreateUserRequest() throws JsonProcessingException {

    if (this.scenarioContext.rawRequestBody == null) {
      this.scenarioContext.rawRequestBody =
          objectMapper.writeValueAsString(this.scenarioContext.requestPayload);
    }

    this.scenarioContext.response = restApiClient
        .post(
            ApiPaths.APP_USERS_PATH,
            this.scenarioContext.requestContentType,
            this.scenarioContext.rawRequestBody
        );
  }
}
