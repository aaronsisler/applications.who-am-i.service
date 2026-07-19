package com.ebsolutions.applications.whoami.nonfunctional;

import static org.assertj.core.api.Assertions.assertThat;

import com.ebsolutions.applications.whoami.dto.ErrorCode;
import com.ebsolutions.applications.whoami.dto.ErrorDto;
import com.ebsolutions.applications.whoami.testfixture.context.BaseSteps;
import com.ebsolutions.applications.whoami.testfixture.context.PlaceholderTokens;
import com.ebsolutions.applications.whoami.testfixture.context.ScenarioContext;
import com.ebsolutions.applications.whoami.testfixture.http.RestApiClient;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpMethod;

@RequiredArgsConstructor
public class ApplicationEndpointsIntegrationSteps extends BaseSteps {

  private final ObjectMapper objectMapper;
  private final RestApiClient restApiClient;
  private final ScenarioContext scenarioContext;

  @When("the client makes a {string} request to {string}")
  public void theClientMakesAHttpMethodRequestToEndpoint(String httpMethod, String endpoint) {

    if (!HttpMethod.GET.matches(httpMethod)) {
      throw new IllegalArgumentException("Unsupported HTTP method: " + httpMethod);
    }

    this.scenarioContext.response = this.restApiClient.get(endpoint);

  }

  @Then("the response status code should be {int}")
  public void theResponseStatusCodeShouldBe(int responseStatusCode) {

    assertThat(this.scenarioContext.response.statusCode()).isEqualTo(responseStatusCode);

  }

  @And("the response body should include an error code {string}")
  public void theResponseBodyShouldIncludeAnErrorCode(String rawErrorCode)
      throws JsonProcessingException {

    if (PlaceholderTokens.NOT_APPLICABLE_IDENTIFIER.equals(rawErrorCode)) {
      return;
    }

    ErrorDto errorDto = objectMapper
        .readValue(this.scenarioContext.response.body(), ErrorDto.class);

    assertThat(errorDto).isNotNull();
    assertThat(errorDto.getErrors()).isNotEmpty();

    ErrorCode errorCode = ErrorCode.valueOf(rawErrorCode);

    assertThat(errorDto.getErrors().getFirst().getCode()).isEqualTo(errorCode);
  }

}