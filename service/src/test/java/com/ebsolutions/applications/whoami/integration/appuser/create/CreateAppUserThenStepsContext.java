package com.ebsolutions.applications.whoami.integration.appuser.create;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

import com.ebsolutions.applications.whoami.appuser.core.AppUser;
import com.ebsolutions.applications.whoami.dto.ErrorDto;
import com.ebsolutions.applications.whoami.integration.IntegrationStepsContext;
import com.fasterxml.jackson.core.JsonProcessingException;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import java.io.UnsupportedEncodingException;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;


public class CreateAppUserThenStepsContext extends IntegrationStepsContext {
  @Then("the create-user response status should be {int}")
  public void theCreateUserResponseStatusShouldBe(int statusCode) {
    assertThat(integrationScenarioContext.responses.getFirst().getStatus()).isEqualTo(statusCode);
  }

  @And("the create-user response error message should contain {string}")
  public void theCreateUserResponseErrorMessageShouldContain(String errorMessage)
      throws UnsupportedEncodingException {
    assertThat(integrationScenarioContext.responses.getFirst().getContentAsString()).contains(
        errorMessage);
  }

  @And("the data store was not called to save the new user")
  public void theDataStoreWasNotCalledToSaveTheNewUser() {
    verify(appUserRepository, never()).save(any());
  }

  @Then("each create-user response should include unique client-facing identifiers")
  public void eachCreateUserResponseShouldIncludeUniqueClientFacingIdentifiers() {
    Set<String> contentResponses = integrationScenarioContext.responses.stream()
        .map(response -> {
          try {
            return response.getContentAsString();
          } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
          }
        })
        .collect(Collectors.toSet());


    assertThat(contentResponses)
        .as("Each response should be unique")
        .hasSize(integrationScenarioContext.responses.size());

    Set<AppUser> appUsers = contentResponses.stream().map(contentResponse -> {
      try {
        return objectMapper.readValue(contentResponse, AppUser.class);
      } catch (Exception e) {
        throw new RuntimeException(e);
      }
    }).collect(Collectors.toSet());

    assertThat(appUsers)
        .as("Each app user should be unique")
        .hasSize(integrationScenarioContext.responses.size());

    Set<UUID> externalIds = appUsers.stream()
        .map(AppUser::getExternalId)
        .collect(Collectors.toSet());

    assertThat(externalIds)
        .as("Each app user's external id should be unique")
        .hasSize(integrationScenarioContext.responses.size());
  }

  @And("the create-user response should contain exactly {int} error")
  public void theCreateUserResponseShouldContainExactlyError(int errorCount)
      throws UnsupportedEncodingException, JsonProcessingException {
    assertThat(integrationScenarioContext.responses.size()).isEqualTo(1);

    ErrorDto errorDto = objectMapper
        .readValue(integrationScenarioContext.responses.getFirst().getContentAsString(),
            ErrorDto.class);

    assertThat(errorDto.getErrors()).hasSize(errorCount);
  }

  @And("the create-user response should contain an error with:")
  public void theCreateUserResponseShouldContainAnErrorWith(DataTable dataTable)
      throws UnsupportedEncodingException, JsonProcessingException {

    Map<String, String> expected = dataTable.asMap(String.class, String.class);

    String expectedField = expected.get("field");
    String expectedCode = expected.get("code");

    ErrorDto errorDto = objectMapper
        .readValue(integrationScenarioContext.responses.getFirst().getContentAsString(),
            ErrorDto.class);

    assertThat(errorDto).isNotNull();
    assertThat(errorDto.getErrors()).isNotEmpty();

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
