package com.ebsolutions.applications.whoami.common.appuser.create;

import static org.assertj.core.api.Assertions.assertThat;

import com.ebsolutions.applications.whoami.common.BaseStepsContext;
import io.cucumber.java.en.Then;

public class CreateAppUserThenStepsContext extends BaseStepsContext {

  @Then("the create-user response status should be {int}")
  public void theCreateUserResponseStatusShouldBe(int statusCode) {
    assertThat(this.scenarioContext.response.statusCode())
        .isEqualTo(statusCode);
  }
}
