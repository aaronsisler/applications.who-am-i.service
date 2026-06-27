package com.ebsolutions.applications.whoami.core;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.ebsolutions.applications.whoami.dto.ErrorCode;
import com.ebsolutions.applications.whoami.testfixture.context.CommonContext;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class ErrorCodeMapperSteps extends CommonContext {

  private final ErrorCodeMapper errorCodeMapper;

  private ErrorCode errorCode;
  private String value;

  @Given("a string value {string} is provided")
  public void aStringValueIsProvided(String value) {

    this.value = normalizeTestFixture(value);

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