package com.ebsolutions.applications.whoami.acceptance.appuser.create;

import com.ebsolutions.applications.whoami.common.CommonContext;
import com.ebsolutions.applications.whoami.common.testfixture.ScenarioContext;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.Before;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import lombok.RequiredArgsConstructor;


@RequiredArgsConstructor
public class CreateAppUserGivenStepsContext extends CommonContext {

  private final ScenarioContext scenarioContext;

  @Before
  public void beforeScenario() {
    this.scenarioContext.reset();
  }

  @Given("the client provides a create-user request body with the following fields:")
  public void theClientProvidesACreateUserRequestBodyWithTheFollowingFields(DataTable dataTable) {

    dataTable.asMap().forEach((key, value) ->
        this.scenarioContext.requestPayload.put(key, normalizeTestFixture(value))
    );
  }

  @And("the create-user request has a content type of {string}")
  public void theCreateUserRequestHasAContentTypeOf(String mediaType) {
    this.scenarioContext.requestContentType = normalizeMediaType(mediaType);
  }
}
