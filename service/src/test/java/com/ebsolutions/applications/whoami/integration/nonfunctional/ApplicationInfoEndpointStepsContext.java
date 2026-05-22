package com.ebsolutions.applications.whoami.integration.nonfunctional;

import static org.assertj.core.api.Assertions.assertThat;

import com.ebsolutions.applications.whoami.core.config.ApiPaths;
import com.ebsolutions.applications.whoami.dto.ApplicationInfo;
import com.ebsolutions.applications.whoami.dto.BuildMetadata;
import com.ebsolutions.applications.whoami.integration.IntegrationStepsContext;
import com.fasterxml.jackson.core.JsonProcessingException;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.jupiter.api.Assertions;
import org.springframework.http.HttpStatus;

public class ApplicationInfoEndpointStepsContext extends IntegrationStepsContext {

  @When("the info endpoint is invoked")
  public void theInfoEndpointIsInvoked() {
    this.scenarioContext.response = this.restApiClient.get(ApiPaths.APPLICATION_INFO_PATH);
  }

  @Then("the correct info response is returned")
  public void theCorrectInfoResponseIsReturned() throws JsonProcessingException {

    Assertions.assertEquals(HttpStatus.OK.value(), this.scenarioContext.response.statusCode());

    ApplicationInfo applicationInfo = objectMapper
        .readValue(this.scenarioContext.response.body(), ApplicationInfo.class);

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
