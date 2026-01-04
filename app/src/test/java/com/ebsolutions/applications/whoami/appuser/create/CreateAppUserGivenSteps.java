package com.ebsolutions.applications.whoami.appuser.create;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import com.ebsolutions.applications.whoami.appuser.core.AppUser;
import com.ebsolutions.applications.whoami.tooling.BaseSteps;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import java.time.LocalDateTime;
import java.util.UUID;

public class CreateAppUserGivenSteps extends BaseSteps {
  @Given("a valid create-user request contains the following fields:")
  public void aValidCreateUserRequestContainsTheFollowingFields(DataTable dataTable) {
    // Convert table to map
    scenarioContext.requestPayload.putAll(dataTable.asMap());
  }

  @And("the create-user request is missing the {string} field")
  public void theCreateUserRequestIsMissingTheField(String missingField) {
    // Remove the missing field
    scenarioContext.requestPayload.remove(missingField);
  }

  @And("the data store returns the created user")
  public void theDataStoreReturnsTheCreatedUser() {
    when(appUserRepository.save(any())).thenAnswer(invocation -> createAppUserFromPayload());
  }

  private AppUser createAppUserFromPayload() {
    return AppUser
        .builder()
        .id(1L)
        .userId(UUID.randomUUID())
        .emailAddress((String) scenarioContext.requestPayload.get("emailAddress"))
        .firstName((String) scenarioContext.requestPayload.get("firstName"))
        .lastName((String) scenarioContext.requestPayload.get("lastName"))
        .createdAt(LocalDateTime.now())
        .updatedAt(LocalDateTime.now())
        .build();
  }
}
