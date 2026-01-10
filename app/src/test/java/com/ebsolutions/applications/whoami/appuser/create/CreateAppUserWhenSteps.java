package com.ebsolutions.applications.whoami.appuser.create;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

import com.ebsolutions.applications.whoami.tooling.BaseSteps;
import io.cucumber.java.en.When;
import org.springframework.http.MediaType;

public class CreateAppUserWhenSteps extends BaseSteps {
  @When("the client submits the create-user request")
  public void theClientSubmitsTheCreateUserRequest() throws Exception {
    String json = objectMapper.writeValueAsString(scenarioContext.requestPayload);

    System.out.println(json);

    scenarioContext.latestResponse = mockMvc.perform(post("/app-users")
            .contentType(MediaType.APPLICATION_JSON)
            .content(json))
        .andReturn()
        .getResponse();
  }
}
