package com.ebsolutions.applications.whoami.appuser.create;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import com.ebsolutions.applications.whoami.tooling.BaseSteps;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import java.util.Map;
import java.util.stream.Collectors;
import org.springframework.dao.DataAccessResourceFailureException;

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

  @And("the data store is unavailable")
  public void theDataStoreIsUnavailable() {
    var exception = new DataAccessResourceFailureException("Something blew up");

    when(appUserRepository.save(any()))
        .thenThrow(exception);
  }
}
