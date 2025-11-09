package com.ebsolutions.applications.whoami.tooling;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.ebsolutions.applications.whoami.config.UriConstants;
import com.ebsolutions.applications.whoami.model.ApplicationInfo;
import com.ebsolutions.applications.whoami.model.BuildMetadata;
import com.fasterxml.jackson.core.JsonProcessingException;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import java.io.UnsupportedEncodingException;
import org.junit.jupiter.api.Assertions;
import org.springframework.http.HttpStatus;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MvcResult;

public class ActuatorSteps extends BaseTest {
  protected MvcResult result;

  @Given("application is up")
  public void applicationIsUp() throws Exception {
    mockMvc.perform(get(UriConstants.HEALTH_CHECK_URI))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.status", is("UP")));
  }

  @When("the info endpoint is invoked")
  public void theInfoEndpointIsInvoked() throws Exception {
    result = mockMvc.perform(get(UriConstants.INFO_CHECK_URI)).andReturn();
  }

  @Then("the correct info response is returned")
  public void theCorrectInfoResponseIsReturned()
      throws UnsupportedEncodingException, JsonProcessingException {
    MockHttpServletResponse mockHttpServletResponse = result.getResponse();

    Assertions.assertEquals(HttpStatus.OK.value(), mockHttpServletResponse.getStatus());

    String content = mockHttpServletResponse.getContentAsString();
    ApplicationInfo applicationInfo = objectMapper.readValue(content, ApplicationInfo.class);

    assertThat(applicationInfo).isNotNull();
    assertThat(applicationInfo.getBuild()).isNotNull();

    BuildMetadata buildMetadata = applicationInfo.getBuild();

    assertThat(buildMetadata.getGroup()).isEqualTo("com.ebsolutions.applications.whoami");
    assertThat(buildMetadata.getArtifact()).isEqualTo("who-am-i-service");
    assertThat(buildMetadata.getName()).isEqualTo("Who Am I Service");
    assertThat(buildMetadata.getTime()).isNotNull();

    // Strict numeric version assertion
    assertThat(buildMetadata.getVersion())
        .as("Version should follow strict numeric X.Y.Z format")
        .matches("^\\d+\\.\\d+\\.\\d+$");
  }
}
