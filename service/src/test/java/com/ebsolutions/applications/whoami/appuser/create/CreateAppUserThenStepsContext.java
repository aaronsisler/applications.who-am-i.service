package com.ebsolutions.applications.whoami.appuser.create;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

import com.ebsolutions.applications.whoami.appuser.core.AppUser;
import com.ebsolutions.applications.whoami.appuser.core.AppUserRepository;
import com.ebsolutions.applications.whoami.dto.AppUserDto;
import com.ebsolutions.applications.whoami.dto.ErrorDto;
import com.ebsolutions.applications.whoami.testfixture.ScenarioResponse;
import com.ebsolutions.applications.whoami.testfixture.context.CommonContext;
import com.ebsolutions.applications.whoami.testfixture.context.ScenarioContext;
import com.ebsolutions.applications.whoami.testfixture.integration.IntegrationScenarioContext;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.assertj.core.api.AssertionsForClassTypes;

@RequiredArgsConstructor
public class CreateAppUserThenStepsContext extends CommonContext {

  private final ObjectMapper objectMapper;
  private final ScenarioContext scenarioContext;
  private final AppUserRepository appUserRepository;
  private final IntegrationScenarioContext integrationScenarioContext;

  @Then("the create-user response status should be {int}")
  public void theCreateUserResponseStatusShouldBe(int statusCode) {
    AssertionsForClassTypes.assertThat(this.scenarioContext.response.statusCode())
        .isEqualTo(statusCode);
  }

  @And("the create-user response body should contain:")
  public void theCreateUserResponseBodyShouldContain(DataTable dataTable)
      throws JsonProcessingException {

    var responseBody = this.scenarioContext.response.body();

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

    var responseBody = this.scenarioContext.response.body();

    assertThat(responseBody).isNotNull();

    AppUserDto appUser = this.objectMapper.readValue(responseBody, AppUserDto.class);

    assertThat(appUser.getExternalId()).isNotNull();
  }

  @And("the create-user response should contain a valid createdAt timestamp")
  public void theCreateUserResponseShouldContainAValidCreatedAtTimestamp()
      throws JsonProcessingException {

    var responseBody = this.scenarioContext.response.body();

    assertThat(responseBody).isNotNull();

    AppUserDto appUser = this.objectMapper.readValue(responseBody, AppUserDto.class);

    assertThat(appUser.getCreatedAt()).isNotNull();
  }


  @And("the create-user response should contain a valid updatedAt timestamp")
  public void theCreateUserResponseShouldContainAValidUpdatedAtTimestamp()
      throws JsonProcessingException {

    var responseBody = this.scenarioContext.response.body();

    assertThat(responseBody).isNotNull();

    AppUserDto appUser = this.objectMapper.readValue(responseBody, AppUserDto.class);

    assertThat(appUser.getUpdatedAt()).isNotNull();
  }

  @And("the create-user response should contain exactly {int} error")
  public void theCreateUserResponseShouldContainExactlyError(int errorCount)
      throws JsonProcessingException {

    assertThat(this.scenarioContext.response).isNotNull();

    ErrorDto errorDto = objectMapper
        .readValue(this.scenarioContext.response.body(), ErrorDto.class);

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
        .readValue(this.scenarioContext.response.body(), ErrorDto.class);

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

  @And("the create-user response error message should contain {string}")
  public void theCreateUserResponseErrorMessageShouldContain(String errorMessage) {

    assertThat(this.scenarioContext.response.body()).contains(errorMessage);

  }

  @And("the data store was not called to save the new user")
  public void theDataStoreWasNotCalledToSaveTheNewUser() {

    verify(appUserRepository, never()).save(any());

  }

  @Then("each create-user response should include unique client-facing identifiers")
  public void eachCreateUserResponseShouldIncludeUniqueClientFacingIdentifiers() {

    Set<String> contentResponses = this.integrationScenarioContext.responses.stream()
        .map(ScenarioResponse::body)
        .collect(Collectors.toSet());

    assertThat(contentResponses)
        .as("Each response should be unique")
        .hasSize(this.integrationScenarioContext.responses.size());

    Set<AppUser> appUsers = contentResponses.stream().map(contentResponse -> {
      try {
        return objectMapper.readValue(contentResponse, AppUser.class);
      } catch (Exception e) {
        throw new RuntimeException(e);
      }
    }).collect(Collectors.toSet());

    assertThat(appUsers)
        .as("Each app user should be unique")
        .hasSize(this.integrationScenarioContext.responses.size());

    Set<UUID> externalIds = appUsers.stream()
        .map(AppUser::getExternalId)
        .collect(Collectors.toSet());

    assertThat(externalIds)
        .as("Each app user's external id should be unique")
        .hasSize(this.integrationScenarioContext.responses.size());
  }
}
