package com.ebsolutions.applications.whoami.appuser.create;

import static org.assertj.core.api.Assertions.assertThat;

import com.ebsolutions.applications.whoami.tooling.BaseSteps;
import com.fasterxml.jackson.core.type.TypeReference;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import java.io.UnsupportedEncodingException;
import java.util.Map;


public class CreateAppUserThenSteps extends BaseSteps {
  @And("the response body should contain:")
  public void theResponseBodyShouldContain(DataTable dataTable)
      throws UnsupportedEncodingException {
    // expected values from feature file
    Map<String, String> expected = dataTable.asMap(String.class, String.class);

    // actual response body as a Map
    Map<String, Object> actual =
        objectMapper.convertValue(
            scenarioContext.latestResponse.getContentAsString(),
            new TypeReference<>() {
            }
        );

    expected.forEach((key, expectedValue) -> {
      assertThat(actual)
          .as("Response body should contain key '%s'", key)
          .containsKey(key);

      assertThat(actual.get(key))
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
}
