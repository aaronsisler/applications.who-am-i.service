package com.ebsolutions.applications.whoami.appuser.create;

import com.ebsolutions.applications.whoami.support.StepsContext;
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

  @And("the create-user request has a content type of {string}")
  public void theCreateUserRequestHasAContentTypeOf(String mediaType) {
    scenarioContext.mediaType = switch (mediaType) {
      case MediaType.APPLICATION_JSON_VALUE -> MediaType.APPLICATION_JSON;
      case MediaType.TEXT_PLAIN_VALUE -> MediaType.TEXT_PLAIN;
      default -> throw new IllegalArgumentException("Unsupported content type: " + mediaType);
    };
  }

  @Given("a create-user request with malformed JSON")
  public void aCreateUserRequestWithMalformedJSON() {
    scenarioContext.requestPayload.put("taco", "{invalid-json:");
  }
}
