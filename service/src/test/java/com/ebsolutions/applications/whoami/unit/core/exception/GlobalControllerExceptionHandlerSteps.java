package com.ebsolutions.applications.whoami.unit.core.exception;

import com.ebsolutions.applications.whoami.unit.UnitStepsContext;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class GlobalControllerExceptionHandlerSteps extends UnitStepsContext {
  @Given("a validation code {string} is provided")
  public void aValidationCodeIsProvided(String validationCode) {
    System.out.println("Validation code: " + validationCode);
  }

  @When("the code is mapped to an error code")
  public void theCodeIsMappedToAnErrorCode() {
    System.out.println("Mapping validation code to error code...");
  }

  @Then("the resulting error code should be {string}")
  public void theResultingErrorCodeShouldBe(String errorCode) {
    System.out.println("Error code: " + errorCode);
  }
}