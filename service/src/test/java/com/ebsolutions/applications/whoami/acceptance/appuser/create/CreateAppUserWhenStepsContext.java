package com.ebsolutions.applications.whoami.acceptance.appuser.create;

import com.ebsolutions.applications.whoami.acceptance.AcceptanceStepsContext;
import com.ebsolutions.applications.whoami.core.config.ApiPaths;
import io.cucumber.java.en.When;
import java.nio.charset.StandardCharsets;
import org.springframework.http.ResponseEntity;

public class CreateAppUserWhenStepsContext extends AcceptanceStepsContext {

  @When("the client submits the create-user request")
  public void theClientSubmitsTheCreateUserRequest() {

    try {
      this.acceptanceScenarioContext.response = restClient
          .post()
          .uri(ApiPaths.APP_USERS_PATH)
          .contentType(this.acceptanceScenarioContext.requestContentType)
          .body(this.acceptanceScenarioContext.requestPayload)
          .exchange((request, response) -> {
            String body = new String(response.getBody().readAllBytes(), StandardCharsets.UTF_8);

            return ResponseEntity
                .status(response.getStatusCode())
                .headers(response.getHeaders())
                .body(body);
          });
    } catch (Exception e) {
      System.out.println("Error during request submission: " + e.getMessage());
    }
  }
}
