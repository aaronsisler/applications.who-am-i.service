package com.ebsolutions.applications.whoami.integration.system;

import static org.assertj.core.api.Assertions.assertThat;

import com.ebsolutions.applications.whoami.common.testfixture.ScenarioResponse;
import com.ebsolutions.applications.whoami.dto.ErrorCode;
import com.ebsolutions.applications.whoami.dto.ErrorDto;
import com.ebsolutions.applications.whoami.integration.IntegrationStepsContext;
import com.fasterxml.jackson.core.JsonProcessingException;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

public class ApplicationSystemEndpointsIntegrationStepsContext extends IntegrationStepsContext {

  @When("the client makes a {string} request to {string}")
  public void theClientMakesAHttpMethodRequestToEndpoint(String httpMethod, String endpoint)
      throws Exception {

    MockHttpServletRequestBuilder requestBuilder = switch (httpMethod.toUpperCase()) {
      case "GET" -> MockMvcRequestBuilders.get(endpoint);
      case "POST" -> MockMvcRequestBuilders.post(endpoint);
      case "PUT" -> MockMvcRequestBuilders.put(endpoint);
      case "DELETE" -> MockMvcRequestBuilders.delete(endpoint);
      case "PATCH" -> MockMvcRequestBuilders.patch(endpoint);
      default -> throw new IllegalArgumentException("Unsupported HTTP method: " + httpMethod);
    };

    MockHttpServletResponse mockHttpServletResponse = mockMvc
        .perform(requestBuilder).andReturn().getResponse();

    this.scenarioContext.response = new ScenarioResponse(
        mockHttpServletResponse.getStatus(),
        mockHttpServletResponse.getContentAsString(),
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