package com.ebsolutions.applications.whoami.appuser.create;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import com.ebsolutions.applications.whoami.appuser.core.AppUser;
import com.ebsolutions.applications.whoami.tooling.BaseSteps;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import java.time.LocalDateTime;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

public class CreateAppUserGivenSteps extends BaseSteps {
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

  // Not used
  @And("the data store returns the created user")
  public void theDataStoreReturnsTheCreatedUser() {
    when(appUserRepository.save(any()))
        .thenAnswer(invocation -> createAppUserFromPayload());
  }


  // Not used
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
