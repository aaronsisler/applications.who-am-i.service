package com.ebsolutions.applications.whoami.appuser.create;

import com.ebsolutions.applications.whoami.tooling.BaseTest;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;

public class CreateAppUserGivenSteps extends BaseTest {
  @Given("a valid create-user request contains the following fields:")
  public void aValidCreateUserRequestContainsTheFollowingFields(DataTable dataTable) {
    // Convert table to map
    dataTable.asMaps().forEach(row -> requestPayload.putAll(row));
  }

  @And("the create-user request is missing the {string} field")
  public void theCreateUserRequestIsMissingTheField(String missingField) {
    // Remove the missing field
    requestPayload.remove(missingField);
  }
}
