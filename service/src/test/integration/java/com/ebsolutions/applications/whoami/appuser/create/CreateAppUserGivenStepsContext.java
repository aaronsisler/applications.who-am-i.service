package com.ebsolutions.applications.whoami.appuser.create;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import com.ebsolutions.applications.whoami.support.StepsContext;
import com.fasterxml.jackson.core.JsonProcessingException;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.Before;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import java.util.Map;
import java.util.stream.Collectors;
import org.mockito.Mockito;
import org.springframework.http.MediaType;

public class CreateAppUserGivenStepsContext extends StepsContext {
  @Before
  public void beforeScenario() {
    scenarioContext.reset();
    Mockito.reset(appUserRepository);
  }

  @Given("the client provides a create-user request body with the following fields:")
  public void theClientProvidesACreateUserRequestBodyWithTheFollowingFields(DataTable dataTable) {
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

  @And("the create-user request has a content type of {string}")
  public void theCreateUserRequestHasAContentTypeOf(String mediaType) {
    scenarioContext.mediaType = switch (mediaType) {
      case MediaType.APPLICATION_JSON_VALUE -> MediaType.APPLICATION_JSON;
      case MediaType.TEXT_PLAIN_VALUE -> MediaType.TEXT_PLAIN;
      default -> throw new IllegalArgumentException("Unsupported content type: " + mediaType);
    };
  }

  @Given("a create-user request body with malformed JSON")
  public void aCreateUserRequestBodyWithMalformedJSON() {
    scenarioContext.requestPayload.put("taco", "{invalid-json:");
  }

  @Given("the client provides two unique create-user request bodies")
  public void theClientProvidesTwoUniqueCreateUserRequestBodies() throws JsonProcessingException {
    Map<String, Object> firstRequestBodyContent = Map.of(
        "emailAddress", "johnny.appleseed@gmail.com",
        "firstName", "Johnny",
        "lastName", "Appleseed");

    Map<String, Object> secondRequestBodyContent = Map.of(
        "emailAddress", "not.johnny.appleseed@gmail.com",
        "firstName", "Not Johnny",
        "lastName", "Appleseed");

    scenarioContext.listOfRequestContents
        .add(objectMapper.writeValueAsString(firstRequestBodyContent));

    scenarioContext.listOfRequestContents
        .add(objectMapper.writeValueAsString(secondRequestBodyContent));
  }

  @And("the data store is configured to save the new users")
  public void theDataStoreIsConfiguredToSaveTheNewUsers() {
    when(appUserRepository.save(any()))
        .thenAnswer(invocation -> invocation.getArgument(0));
  }
}
