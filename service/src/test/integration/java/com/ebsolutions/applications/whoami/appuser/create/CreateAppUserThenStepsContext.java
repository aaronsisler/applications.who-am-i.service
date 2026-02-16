package com.ebsolutions.applications.whoami.appuser.create;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

import com.ebsolutions.applications.whoami.appuser.core.AppUser;
import com.ebsolutions.applications.whoami.support.StepsContext;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import java.io.UnsupportedEncodingException;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;


public class CreateAppUserThenStepsContext extends StepsContext {
  @And("the response body should contain:")
  public void theResponseBodyShouldContain(DataTable dataTable) {
    // expected values from feature file
    Map<String, String> expected = dataTable.asMap(String.class, String.class);

    expected.forEach((key, expectedValue) -> {
      assertThat(scenarioContext.responseBody)
          .as("Response body should contain key '%s'", key)
          .containsKey(key);

      assertThat(scenarioContext.responseBody.get(key))
          .as("Value for key '%s'", key)
          .isNotNull()
          .hasToString(expectedValue);
    });
  }

  @Then("the create-user response status should be {int}")
  public void theCreateUserResponseStatusShouldBe(int statusCode) {
    assertThat(scenarioContext.responses.getFirst().getStatus()).isEqualTo(statusCode);
  }

  @And("the create-user response error message should contain {string}")
  public void theCreateUserResponseErrorMessageShouldContain(String errorMessage)
      throws UnsupportedEncodingException {
    assertThat(scenarioContext.responses.getFirst().getContentAsString()).contains(errorMessage);
  }

  @And("the data store was not called to save the new user")
  public void theDataStoreWasNotCalledToSaveTheNewUser() {
    verify(appUserRepository, never()).save(any());
  }

  @Then("each create-user response should include unique client-facing identifiers")
  public void eachCreateUserResponseShouldIncludeUniqueClientFacingIdentifiers() {
    Set<String> contentResponses = scenarioContext.responses.stream()
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
        .hasSize(scenarioContext.responses.size());

    Set<AppUser> appUsers = contentResponses.stream().map(contentResponse -> {
      try {
        return objectMapper.readValue(contentResponse, AppUser.class);
      } catch (Exception e) {
        throw new RuntimeException(e);
      }
    }).collect(Collectors.toSet());

    assertThat(appUsers)
        .as("Each app user should be unique")
        .hasSize(scenarioContext.responses.size());

    Set<UUID> externalIds = appUsers.stream()
        .map(AppUser::getExternalId)
        .collect(Collectors.toSet());

    assertThat(externalIds)
        .as("Each app user's external id should be unique")
        .hasSize(scenarioContext.responses.size());
  }
}
