package com.ebsolutions.applications.whoami.appuser.create;

import static org.assertj.core.api.Assertions.assertThat;

import com.ebsolutions.applications.whoami.tooling.BaseSteps;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import java.io.UnsupportedEncodingException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;


public class CreateAppUserThenSteps extends BaseSteps {
  @And("the response body should contain:")
  public void theResponseBodyShouldContain(DataTable dataTable) {
    // expected values from feature file
    Map<String, String> expected = dataTable.asMap(String.class, String.class);

    expected.forEach((key, expectedValue) -> {
      assertThat(scenarioContext.responseBody)
          .as("Response body should contain key '%s'", key)
          .containsKey(key);

      assertThat(scenarioContext.responseBody.get(key))
          .as("Value for key '%s'", key)
          .isNotNull()
          .hasToString(expectedValue);
    });
  }

  @Then("the create-user response status should be {int}")
  public void theCreateUserResponseStatusShouldBe(int statusCode) {
    assertThat(scenarioContext.latestResponse.getStatus()).isEqualTo(statusCode);
  }

  @And("the create-user response error message should contain {string}")
  public void theCreateUserResponseErrorMessageShouldContain(String errorMessage)
      throws UnsupportedEncodingException {
    assertThat(scenarioContext.latestResponse.getContentAsString()).contains(errorMessage);
  }

  @And("the client facing id should be returned in the response")
  public void theClientFacingIdShouldBeReturnedInTheResponse() {
    assertThat(scenarioContext.responseBody)
        .as("Response body should contain key 'userId'")
        .containsKey("userId");

    assertThat(scenarioContext.responseBody.get("userId"))
        .as("Value for key 'userId'")
        .isNotNull()
        .hasToString(String.valueOf(MOCKED_UUID));
  }

  @And("the create-user response should have a body")
  public void theCreateUserResponseShouldHaveABody()
      throws UnsupportedEncodingException, JsonProcessingException {

    scenarioContext.responseBody = objectMapper.readValue(
        scenarioContext.latestResponse.getContentAsString(),
        new TypeReference<>() {
        }
    );

  }

  @And("the correct created at timestamp should be returned in the response")
  public void theCorrectCreatedAtTimestampShouldBeReturnedInTheResponse() {
    assertThat(scenarioContext.responseBody)
        .as("Response body should contain key 'createdAt'")
        .containsKey("createdAt");

    @SuppressWarnings("unchecked")
    var createdAtParts = (List<Integer>) scenarioContext.responseBody.get("createdAt");

    var createdAt =
        LocalDateTime.of(
            createdAtParts.get(0),
            createdAtParts.get(1),
            createdAtParts.get(2),
            createdAtParts.get(3),
            createdAtParts.get(4)
        );

    assertThat(createdAt)
        .as("Value for key 'createdAt'")
        .isEqualTo(MOCKED_NOW);
  }

  @And("the correct updated at timestamp should be returned in the response")
  public void theCorrectUpdatedAtTimestampShouldBeReturnedInTheResponse() {
    assertThat(scenarioContext.responseBody)
        .as("Response body should contain key 'updatedAt'")
        .containsKey("updatedAt");

    @SuppressWarnings("unchecked")
    var updatedAtParts = (List<Integer>) scenarioContext.responseBody.get("updatedAt");

    var updatedAt =
        LocalDateTime.of(
            updatedAtParts.get(0),
            updatedAtParts.get(1),
            updatedAtParts.get(2),
            updatedAtParts.get(3),
            updatedAtParts.get(4)
        );

    assertThat(updatedAt)
        .as("Value for key 'updatedAt'")
        .isEqualTo(MOCKED_NOW);
  }
}
