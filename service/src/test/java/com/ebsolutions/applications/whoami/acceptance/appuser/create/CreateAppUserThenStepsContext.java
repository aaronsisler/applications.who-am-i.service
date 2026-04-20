package com.ebsolutions.applications.whoami.acceptance.appuser.create;

import static org.assertj.core.api.Assertions.assertThat;

import com.ebsolutions.applications.whoami.acceptance.AcceptanceStepsContext;
import com.ebsolutions.applications.whoami.dto.AppUserDto;
import com.fasterxml.jackson.core.JsonProcessingException;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;

public class CreateAppUserThenStepsContext extends AcceptanceStepsContext {

  @Then("the create-user response status should be {int}")
  public void theCreateUserResponseStatusShouldBe(int statusCode) {
    assertThat(this.acceptanceScenarioContext.response.getStatusCode().value())
        .isEqualTo(statusCode);
  }

  @And("the create-user response should contain a valid client-facing ID")
  public void theCreateUserResponseShouldContainAValidClientFacingID()
      throws JsonProcessingException {
    var responseBody = this.acceptanceScenarioContext.response.getBody();

    assertThat(responseBody).isNotNull();

    AppUserDto appUser = this.objectMapper.readValue(responseBody, AppUserDto.class);

    assertThat(appUser.getExternalId()).isNotNull();
  }

  @And("the create-user response should contain a valid createdAt timestamp")
  public void theCreateUserResponseShouldContainAValidCreatedAtTimestamp()
      throws JsonProcessingException {
    var responseBody = this.acceptanceScenarioContext.response.getBody();

    assertThat(responseBody).isNotNull();

    AppUserDto appUser = this.objectMapper.readValue(responseBody, AppUserDto.class);

    assertThat(appUser.getCreatedAt()).isNotNull();
  }


  @And("the create-user response should contain a valid updatedAt timestamp")
  public void theCreateUserResponseShouldContainAValidUpdatedAtTimestamp()
      throws JsonProcessingException {
    var responseBody = this.acceptanceScenarioContext.response.getBody();

    assertThat(responseBody).isNotNull();

    AppUserDto appUser = this.objectMapper.readValue(responseBody, AppUserDto.class);

    assertThat(appUser.getUpdatedAt()).isNotNull();
  }
}
