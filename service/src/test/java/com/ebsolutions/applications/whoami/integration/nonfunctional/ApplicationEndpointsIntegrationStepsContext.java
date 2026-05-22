package com.ebsolutions.applications.whoami.integration.nonfunctional;

import static org.assertj.core.api.Assertions.assertThat;

import com.ebsolutions.applications.whoami.common.testfixture.ScenarioResponse;
import com.ebsolutions.applications.whoami.dto.ErrorCode;
import com.ebsolutions.applications.whoami.dto.ErrorDto;
import com.ebsolutions.applications.whoami.integration.IntegrationStepsContext;
import com.fasterxml.jackson.core.JsonProcessingException;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class ApplicationEndpointsIntegrationStepsContext extends IntegrationStepsContext {

  @When("the client makes a {string} request to {string}")
  public void theClientMakesAHttpMethodRequestToEndpointWithId(String httpMethod,
                                                               String endpoint) {

    if (httpMethod.equalsIgnoreCase("GET")) {
      ScenarioResponse response = this.restApiClient.get(endpoint);

      this.scenarioContext.response = new ScenarioResponse(
          response.statusCode(),
          response.body(),
          null
      );
    }

    throw new IllegalArgumentException("Unsupported HTTP method: " + httpMethod);
  }

  @When("the client makes a {string} request to {string} with id {string}")
  public void theClientMakesAHttpMethodRequestToEndpointWithId(String httpMethod,
                                                               String endpoint,
                                                               String id) {

    ScenarioResponse response = switch (httpMethod.toUpperCase()) {
      case "GET" -> this.restApiClient.get(endpoint, id);
      case "POST" -> this.restApiClient.post(endpoint, null, null);
      case "PUT" -> this.restApiClient.put(endpoint, id, null, null);
      case "DELETE" -> this.restApiClient.delete(endpoint, id);
      default -> throw new IllegalArgumentException("Unsupported HTTP method: " + httpMethod);
    };

    this.scenarioContext.response = new ScenarioResponse(
        response.statusCode(),
        response.body(),
        null
    );
  }

  @Then("the response status should be {int}")
  public void theResponseStatusShouldBe(int responseStatusCode) {

    assertThat(this.scenarioContext.response.statusCode()).isEqualTo(responseStatusCode);

  }

  @And("the response body should include an error code {string}")
  public void theResponseBodyShouldIncludeAnErrorCode(String rawErrorCode)
      throws JsonProcessingException {

    ErrorDto errorDto = objectMapper
        .readValue(this.scenarioContext.response.body(), ErrorDto.class);

    assertThat(errorDto).isNotNull();
    assertThat(errorDto.getErrors()).isNotEmpty();

    ErrorCode errorCode = ErrorCode.valueOf(rawErrorCode);

    assertThat(errorDto.getErrors().getFirst().getCode()).isEqualTo(errorCode);
  }
}