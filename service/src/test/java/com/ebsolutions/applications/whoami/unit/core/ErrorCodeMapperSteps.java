package com.ebsolutions.applications.whoami.unit.core;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.ebsolutions.applications.whoami.core.ErrorCodeMapper;
import com.ebsolutions.applications.whoami.dto.ErrorCode;
import com.ebsolutions.applications.whoami.unit.UnitStepsContext;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class ErrorCodeMapperSteps extends UnitStepsContext {

  private final ErrorCodeMapper errorCodeMapper = new ErrorCodeMapper();

  private String value;
  private ErrorCode errorCode;

  @Given("a string value {string} is provided")
  public void aStringValueIsProvided(String value) {
    this.value = normalize(value);
  }

  @When("the string value is mapped to an error code")
  public void theStringValueIsMappedToAnErrorCode() {
    errorCode = errorCodeMapper.map(value);
  }

  @Then("the resulting error code should be {string}")
  public void theResultingErrorCodeShouldBe(String errorCode) {
    assertEquals(ErrorCode.valueOf(errorCode), this.errorCode);
  }
}