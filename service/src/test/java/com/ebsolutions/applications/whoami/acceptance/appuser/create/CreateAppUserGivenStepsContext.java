package com.ebsolutions.applications.whoami.acceptance.appuser.create;

import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;

public class CreateAppUserGivenStepsContext {

  @Given("the client provides a create-user request body with the following fields:")
  public void theClientProvidesACreateUserRequestBodyWithTheFollowingFields(DataTable dataTable) {
    System.out.println("theClientProvidesACreateUserRequestBodyWithTheFollowingFields");
  }

  @And("the create-user request has a content type of {string}")
  public void theCreateUserRequestHasAContentTypeOf(String mediaType) {
    System.out.println("theCreateUserRequestHasAContentTypeOf");
  }
}
