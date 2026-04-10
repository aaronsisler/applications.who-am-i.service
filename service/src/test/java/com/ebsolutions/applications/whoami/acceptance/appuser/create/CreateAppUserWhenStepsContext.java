package com.ebsolutions.applications.whoami.acceptance.appuser.create;

import io.cucumber.java.en.When;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.client.RestClient;

public class CreateAppUserWhenStepsContext {
  @Autowired
  private RestClient restClient;

  @When("the client submits the create-user request")
  public void theClientSubmitsTheCreateUserRequest() {
    System.out.println("theClientSubmitsTheCreateUserRequest");
  }
}
