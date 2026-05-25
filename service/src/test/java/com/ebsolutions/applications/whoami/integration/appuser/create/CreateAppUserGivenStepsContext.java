package com.ebsolutions.applications.whoami.integration.appuser.create;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import com.ebsolutions.applications.whoami.appuser.core.AppUserRepository;
import com.ebsolutions.applications.whoami.common.CommonContext;
import com.ebsolutions.applications.whoami.integration.IntegrationScenarioContext;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import java.util.Map;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class CreateAppUserGivenStepsContext extends CommonContext {

  private final IntegrationScenarioContext integrationScenarioContext;
  private final AppUserRepository appUserRepository;

  @Given("the client provides a create-user request body with the following fields:")
  public void theClientProvidesACreateUserRequestBodyWithTheFollowingFields(DataTable dataTable) {
    dataTable.asMap().forEach((key, value) ->
        integrationScenarioContext.requestPayload.put(key, normalizeTestFixture(value))
    );
  }

  @And("the client provides the create-user request without the {string} field")
  public void theClientProvidesTheCreateUserRequestWithoutTheField(String missingField) {
    // Remove the missing field
    integrationScenarioContext.requestPayload.remove(missingField);
  }

  @And("the create-user request has a content type of {string}")
  public void theCreateUserRequestHasAContentTypeOf(String mediaType) {
    integrationScenarioContext.requestContentType = normalizeMediaType(mediaType);
  }

  @Given("a create-user request body with malformed JSON")
  public void aCreateUserRequestBodyWithMalformedJSON() {
    integrationScenarioContext.requestPayload.put("taco", "{invalid-json:");
  }

  @Given("the client provides two unique create-user request bodies")
  public void theClientProvidesTwoUniqueCreateUserRequestBodies() {

    Map<String, Object> firstRequestBodyContent = Map.of(
        "emailAddress", "johnny.appleseed@gmail.com",
        "firstName", "Johnny",
        "lastName", "Appleseed");

    Map<String, Object> secondRequestBodyContent = Map.of(
        "emailAddress", "not.johnny.appleseed@gmail.com",
        "firstName", "Not Johnny",
        "lastName", "Appleseed");

    integrationScenarioContext.listOfRequestContents.add(firstRequestBodyContent);
    integrationScenarioContext.listOfRequestContents.add(secondRequestBodyContent);

  }

  @And("the data store is configured to save the new users")
  public void theDataStoreIsConfiguredToSaveTheNewUsers() {

    when(appUserRepository.save(any()))
        .thenAnswer(invocation -> invocation.getArgument(0));

  }
}
