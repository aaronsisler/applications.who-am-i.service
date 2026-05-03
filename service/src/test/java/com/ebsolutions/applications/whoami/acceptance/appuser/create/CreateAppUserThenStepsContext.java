package com.ebsolutions.applications.whoami.acceptance.appuser.create;

import static org.assertj.core.api.Assertions.assertThat;

import com.ebsolutions.applications.whoami.acceptance.AcceptanceStepsContext;
import com.ebsolutions.applications.whoami.dto.AppUserDto;
import com.ebsolutions.applications.whoami.dto.ErrorDto;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import java.util.HashMap;
import java.util.Map;

public class CreateAppUserThenStepsContext extends AcceptanceStepsContext {

  @Then("the create-user response status should be {int}")
  public void theCreateUserResponseStatusShouldBe(int statusCode) {
    assertThat(this.acceptanceScenarioContext.response.getStatusCode().value())
        .isEqualTo(statusCode);
  }

  @And("the create-user response body should contain:")
  public void theCreateUserResponseBodyShouldContain(DataTable dataTable)
      throws JsonProcessingException {
    var responseBody = this.acceptanceScenarioContext.response.getBody();

    assertThat(responseBody).isNotNull();

    Map<String, Object> responseBodyMap =
        this.objectMapper.readValue(responseBody, new TypeReference<>() {
        });

    dataTable.asMap().forEach((key, value) ->
        assertThat(responseBodyMap)
            .withFailMessage("Expected response body to contain key '%s'", key)
            .containsKey(key)
    );

    dataTable.asMap().forEach((key, value) ->
        assertThat(responseBodyMap).hasFieldOrPropertyWithValue(key, value));

    AppUserDto appUser = this.objectMapper.convertValue(responseBodyMap, AppUserDto.class);

    assertThat(appUser).isNotNull();
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

  @And("the create-user response should contain exactly {int} error")
  public void theCreateUserResponseShouldContainExactlyError(int errorCount)
      throws JsonProcessingException {
    assertThat(acceptanceScenarioContext.response).isNotNull();

    ErrorDto errorDto = objectMapper
        .readValue(acceptanceScenarioContext.response.getBody(), ErrorDto.class);

    assertThat(errorDto.getErrors()).hasSize(errorCount);
  }

  @And("the create-user response should contain an error with:")
  public void theCreateUserResponseShouldContainAnErrorWith(DataTable dataTable)
      throws JsonProcessingException {

    Map<String, String> expected = new HashMap<>();

    dataTable.asMap(String.class, String.class)
        .forEach((key, value) ->
            expected.put(key, normalizeTestFixture(value))
        );

    ErrorDto errorDto = objectMapper
        .readValue(acceptanceScenarioContext.response.getBody(), ErrorDto.class);

    assertThat(errorDto).isNotNull();
    assertThat(errorDto.getErrors()).isNotEmpty();

    String expectedField = expected.get("field");
    String expectedCode = expected.get("code");

    boolean matchFound = errorDto.getErrors().stream()
        .anyMatch(error ->
            (expectedField == null || expectedField.equals(error.getField()))
                && (expectedCode == null || expectedCode.equals(error.getCode().name()))
        );

    assertThat(matchFound)
        .as("Expected error with field '%s' and code '%s' was not found",
            expectedField, expectedCode)
        .isTrue();
  }
}
