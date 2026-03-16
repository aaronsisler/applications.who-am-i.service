package com.ebsolutions.applications.whoami.integration.appuser.create;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

import com.ebsolutions.applications.whoami.config.ApiPaths;
import com.ebsolutions.applications.whoami.integration.IntegrationStepsContext;
import io.cucumber.java.en.When;
import org.springframework.http.MediaType;

public class CreateAppUserWhenStepsContext extends IntegrationStepsContext {
  @When("the client submits the create-user request")
  public void theClientSubmitsTheCreateUserRequest() throws Exception {
    String json = objectMapper.writeValueAsString(integrationScenarioContext.requestPayload);

    integrationScenarioContext.responses.add(
        mockMvc.perform(post(ApiPaths.APP_USERS_PATH)
                .contentType(integrationScenarioContext.mediaType)
                .content(json))
            .andReturn()
            .getResponse()
    );
  }

  @When("the client submits both of the create-user requests")
  public void theClientSubmitsBothOfTheCreateUserRequests() {
    integrationScenarioContext.listOfRequestContents.forEach(requestContent -> {
      try {
        integrationScenarioContext.responses.add(
            mockMvc.perform(post(ApiPaths.APP_USERS_PATH)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(requestContent))
                .andReturn()
                .getResponse()
        );
      } catch (Exception e) {
        throw new RuntimeException(e);
      }
    });
  }
}
