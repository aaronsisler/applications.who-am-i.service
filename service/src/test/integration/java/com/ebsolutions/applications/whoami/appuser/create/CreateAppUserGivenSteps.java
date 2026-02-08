package com.ebsolutions.applications.whoami.appuser.create;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.when;

import com.ebsolutions.applications.whoami.appuser.core.AppUser;
import com.ebsolutions.applications.whoami.tooling.BaseSteps;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.Before;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import java.util.Map;
import java.util.stream.Collectors;
import org.mockito.Mockito;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.data.relational.core.conversion.DbActionExecutionException;
import org.springframework.http.MediaType;

public class CreateAppUserGivenSteps extends BaseSteps {
  @Before
  public void beforeScenario() {
    scenarioContext.reset();
    Mockito.reset(appUserRepository);
  }

  @Given("the client provides a create-user request with the following fields:")
  public void theClientProvidesACreateUserRequestWithTheFollowingFields(DataTable dataTable) {
    scenarioContext.requestPayload.putAll(
        dataTable
            .asMap()
            .entrySet().stream()
            .collect(Collectors
                .toMap(
                    Map.Entry::getKey,
                    e -> normalize(e.getValue())
                )));
  }

  @And("the client provides the create-user request without the {string} field")
  public void theClientProvidesTheCreateUserRequestWithoutTheField(String missingField) {
    // Remove the missing field
    scenarioContext.requestPayload.remove(missingField);
  }

  @And("the data store is unavailable")
  public void theDataStoreIsUnavailable() {
    var exception =
        new DbActionExecutionException(
            null,
            new Exception("Something blew up")
        );

    when(appUserRepository.save(any()))
        .thenThrow(exception);
  }

  @And("the data store contains an app user with the same email address")
  public void theDataStoreContainsAnAppUserWithTheSameEmailAddress() {
    DbActionExecutionException exception =
        new DbActionExecutionException(
            null,
            new DuplicateKeyException("duplicate email")
        );

    when(appUserRepository.save(any()))
        .thenThrow(exception);
  }

  @And("the request has a content type of {string}")
  public void theRequestHasAContentTypeOf(String mediaType) {
    scenarioContext.mediaType = switch (mediaType) {
      case MediaType.APPLICATION_JSON_VALUE -> MediaType.APPLICATION_JSON;
      case MediaType.TEXT_PLAIN_VALUE -> MediaType.TEXT_PLAIN;
      default -> throw new IllegalArgumentException("Unsupported content type: " + mediaType);
    };
  }

  @Given("a create-user request with invalid JSON payload")
  public void aCreateUserRequestWithInvalidJSONPayload() {
    scenarioContext.requestPayload.put("taco", "{invalid-json:");
  }

  @And("the data store is able to save the new user")
  public void theDataStoreIsAbleToSaveTheNewUser() {
    AppUser mockedAppUser = AppUser.builder()
        .userId(MOCKED_UUID)
        .emailAddress((String) scenarioContext.requestPayload.get("emailAddress"))
        .firstName((String) scenarioContext.requestPayload.get("firstName"))
        .lastName((String) scenarioContext.requestPayload.get("lastName"))
        .createdAt(MOCKED_NOW)
        .updatedAt(MOCKED_NOW)
        .build();

    when(appUserRepository
        .save(argThat(appUser -> appUser.getUserId() == null))
    ).thenReturn(mockedAppUser);
  }
}
