package com.ebsolutions.applications.whoami.appuser.create;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

import com.ebsolutions.applications.whoami.tooling.BaseTest;
import io.cucumber.java.en.When;
import org.springframework.http.MediaType;

public class CreateAppUserWhenSteps extends BaseTest {
  @When("I submit the create-user request")
  public void iSubmitTheCreateUserRequest() throws Exception {
    String json = objectMapper.writeValueAsString(requestPayload);

    latestResponse = mockMvc.perform(post("/app-users")
            .contentType(MediaType.APPLICATION_JSON)
            .content(json))
        .andReturn();
  }
}
