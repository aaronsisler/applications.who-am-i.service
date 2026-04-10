package com.ebsolutions.applications.whoami.acceptance.appuser.create;

import io.cucumber.java.en.Then;

public class CreateAppUserThenStepsContext {

  @Then("the create-user response status should be {int}")
  public void theCreateUserResponseStatusShouldBe(int statusCode) {
    System.out.println("theCreateUserResponseStatusShouldBe");
  }
}
