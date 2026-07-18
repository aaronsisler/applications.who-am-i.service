package com.ebsolutions.applications.whoami.appuser.create;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import com.ebsolutions.applications.whoami.appuser.core.AppUserRepository;
import com.ebsolutions.applications.whoami.testfixture.context.BaseSteps;
import com.ebsolutions.applications.whoami.testfixture.context.ScenarioContext;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.Before;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import lombok.RequiredArgsConstructor;


@RequiredArgsConstructor
public class CreateAppUserGivenSteps extends BaseSteps {

  private final ScenarioContext scenarioContext;
  private final AppUserRepository appUserRepository;

  @Before
  public void beforeScenario() {
    this.scenarioContext.reset();
  }

  @Given("the client provides a create-user request body with the following fields:")
  public void theClientProvidesACreateUserRequestBodyWithTheFollowingFields(DataTable dataTable) {

    dataTable.asMap().forEach((key, value) ->
        this.scenarioContext.requestPayload.put(key, resolvePlaceholderToken(value))
    );
  }

  @And("the create-user request has a content type of {string}")
  public void theCreateUserRequestHasAContentTypeOf(String mediaType) {
    this.scenarioContext.requestContentType = resolveMediaType(mediaType);
  }

  @And("the data store is configured to save the new users")
  public void theDataStoreIsConfiguredToSaveTheNewUsers() {

    when(appUserRepository.save(any()))
        .thenAnswer(invocation -> invocation.getArgument(0));

  }
}
