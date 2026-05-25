package com.ebsolutions.applications.whoami.common.appuser.create;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import com.ebsolutions.applications.whoami.common.CommonContext;
import com.ebsolutions.applications.whoami.common.testfixture.ScenarioContext;
import io.cucumber.java.en.Then;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class CreateAppUserThenStepsContext extends CommonContext {

  private final ScenarioContext scenarioContext;

  @Then("the create-user response status should be {int}")
  public void theCreateUserResponseStatusShouldBe(int statusCode) {
    assertThat(this.scenarioContext.response.statusCode())
        .isEqualTo(statusCode);
  }
}
