package com.ebsolutions.applications.whoami.integration.system;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

import com.ebsolutions.applications.whoami.config.ApiPaths;
import com.ebsolutions.applications.whoami.dto.ApplicationInfo;
import com.ebsolutions.applications.whoami.dto.BuildMetadata;
import com.ebsolutions.applications.whoami.integration.IntegrationStepsContext;
import com.fasterxml.jackson.core.JsonProcessingException;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import java.io.UnsupportedEncodingException;
import org.junit.jupiter.api.Assertions;
import org.springframework.http.HttpStatus;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MvcResult;

public class ApplicationInfoEndpointStepsContext extends IntegrationStepsContext {
  protected MvcResult result;

  @When("the info endpoint is invoked")
  public void theInfoEndpointIsInvoked() throws Exception {
    result = mockMvc.perform(get(ApiPaths.APPLICATION_INFO_PATH)).andReturn();
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
