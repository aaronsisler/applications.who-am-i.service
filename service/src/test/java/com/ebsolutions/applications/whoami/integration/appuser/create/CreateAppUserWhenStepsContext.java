package com.ebsolutions.applications.whoami.integration.appuser.create;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

import com.ebsolutions.applications.whoami.common.testfixture.ScenarioResponse;
import com.ebsolutions.applications.whoami.core.config.ApiPaths;
import com.ebsolutions.applications.whoami.integration.IntegrationStepsContext;
import io.cucumber.java.en.When;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;

public class CreateAppUserWhenStepsContext extends IntegrationStepsContext {
  @When("the client submits the create-user request")
  public void theClientSubmitsTheCreateUserRequest() throws Exception {
    String json = objectMapper.writeValueAsString(this.scenarioContext.requestPayload);

    MockHttpServletResponse mockHttpServletResponse = mockMvc
        .perform(
            post(ApiPaths.APP_USERS_PATH)
                .contentType(this.scenarioContext.requestContentType)
                .content(json))
        .andReturn()
        .getResponse();

    this.scenarioContext.response = new ScenarioResponse(
        mockHttpServletResponse.getStatus(),
        mockHttpServletResponse.getContentAsString(),
        null
    );
  }

  @When("the client submits both of the create-user requests")
  public void theClientSubmitsBothOfTheCreateUserRequests() {
    integrationScenarioContext.listOfRequestContents.forEach(requestContent -> {
      try {
        MockHttpServletResponse mockHttpServletResponse = mockMvc
            .perform(
                post(ApiPaths.APP_USERS_PATH)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(requestContent))
            .andReturn()
            .getResponse();

        this.scenarioContext.response = new ScenarioResponse(
            mockHttpServletResponse.getStatus(),
            mockHttpServletResponse.getContentAsString(),
            null
        );
      } catch (Exception e) {
        throw new RuntimeException(e);
      }
    });
  }
}
