package com.ebsolutions.applications.whoami.tooling;

import static org.assertj.core.api.Assertions.assertThat;

import com.ebsolutions.applications.whoami.config.TestConstants;
import com.ebsolutions.applications.whoami.model.ApplicationHealth;
import com.ebsolutions.applications.whoami.model.ApplicationInfo;
import com.ebsolutions.applications.whoami.model.ApplicationStatus;
import com.ebsolutions.applications.whoami.model.BuildMetadata;
import com.ebsolutions.applications.whoami.util.ApiCallTestUtil;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import java.time.Instant;
import org.junit.jupiter.api.Assertions;


public class ActuatorSteps extends BaseStep {
  private ApplicationInfo applicationInfo;

  @Given("application is up")
  public void applicationIsUp() {
    Instant pollingEnd =
        Instant.now().plusMillis(TestConstants.APPLICATION_START_TIME_WAIT_PERIOD_IN_MILLISECONDS);
    do {
      try {
        Thread.sleep(100);
        if (checkIfApplicationIsUp()) {
          return;
        }
        if (Instant.now().isAfter(pollingEnd)) {
          break;
        }
      } catch (InterruptedException e) {
        Thread.currentThread().interrupt();
        throw new RuntimeException("Interrupted while waiting for condition", e);
      }
    } while (!Instant.now().isAfter(pollingEnd));

    Assertions.fail("Application did not come up in time");
  }

  @When("the info endpoint is invoked")
  public void theInfoEndpointIsInvoked() {
    applicationInfo =
        ApiCallTestUtil.get(restClient, TestConstants.INFO_CHECK_URI, ApplicationInfo.class);
  }

  @Then("the correct info response is returned")
  public void theCorrectInfoResponseIsReturned() {
    assertThat(applicationInfo).isNotNull();
    assertThat(applicationInfo.getBuild()).isNotNull();

    BuildMetadata buildMetadata = applicationInfo.getBuild();

    assertThat(buildMetadata.getGroup()).isEqualTo("com.ebsolutions.applications.whoami");
    assertThat(buildMetadata.getArtifact()).isEqualTo("who-am-i-service");
    assertThat(buildMetadata.getName()).isEqualTo("Who Am I Service");

    // Strict numeric version assertion
    assertThat(buildMetadata.getVersion())
        .as("Version should follow strict numeric X.Y.Z format")
        .matches("^\\d+\\.\\d+\\.\\d+$");
  }

  private boolean checkIfApplicationIsUp() {
    try {
      ApplicationHealth applicationHealth =
          ApiCallTestUtil.get(restClient, TestConstants.HEALTH_CHECK_URI, ApplicationHealth.class);

      if (applicationHealth == null) {
        return false;
      }

      return ApplicationStatus.UP.equals(applicationHealth.getStatus());
    } catch (Exception exception) {
      return false;
    }
  }
}
