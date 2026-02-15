package com.ebsolutions.applications.whoami.appuser.create;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

import com.ebsolutions.applications.whoami.config.ApiPaths;
import com.ebsolutions.applications.whoami.support.StepsContext;
import io.cucumber.java.en.When;

public class CreateAppUserWhenStepsContext extends StepsContext {
  @When("the client submits the create-user request")
  public void theClientSubmitsTheCreateUserRequest() throws Exception {
    String json = objectMapper.writeValueAsString(scenarioContext.requestPayload);

    scenarioContext.latestResponse = mockMvc.perform(post(ApiPaths.APP_USERS_PATH)
            .contentType(scenarioContext.mediaType)
            .content(json))
        .andReturn()
        .getResponse();
  }
}
