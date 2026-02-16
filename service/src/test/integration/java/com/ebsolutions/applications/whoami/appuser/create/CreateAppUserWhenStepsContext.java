package com.ebsolutions.applications.whoami.appuser.create;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

import com.ebsolutions.applications.whoami.config.ApiPaths;
import com.ebsolutions.applications.whoami.support.StepsContext;
import io.cucumber.java.en.When;
import org.springframework.http.MediaType;

public class CreateAppUserWhenStepsContext extends StepsContext {
  @When("the client submits the create-user request")
  public void theClientSubmitsTheCreateUserRequest() throws Exception {
    String json = objectMapper.writeValueAsString(scenarioContext.requestPayload);

    scenarioContext.responses.add(
        mockMvc.perform(post(ApiPaths.APP_USERS_PATH)
                .contentType(scenarioContext.mediaType)
                .content(json))
            .andReturn()
            .getResponse()
    );
  }

  @When("the client submits both of the create-user requests")
  public void theClientSubmitsBothOfTheCreateUserRequests() {
    scenarioContext.listOfRequestContents.forEach(requestContent -> {
      try {
        scenarioContext.responses.add(
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
