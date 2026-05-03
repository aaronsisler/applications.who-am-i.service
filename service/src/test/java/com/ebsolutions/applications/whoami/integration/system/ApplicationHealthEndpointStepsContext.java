package com.ebsolutions.applications.whoami.integration.system;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

import com.ebsolutions.applications.whoami.common.TestFixtures;
import com.ebsolutions.applications.whoami.core.config.ApiPaths;
import com.ebsolutions.applications.whoami.integration.IntegrationStepsContext;
import io.cucumber.java.en.Given;
import org.springframework.http.HttpStatus;
import org.springframework.mock.web.MockHttpServletResponse;


public class ApplicationHealthEndpointStepsContext extends IntegrationStepsContext {

  @Given("the application is running")
  public void theApplicationIsRunning() throws Exception {
    MockHttpServletResponse response = mockMvc.perform(get(ApiPaths.APPLICATION_HEALTH_PATH))
        .andReturn()
        .getResponse();

    assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());

    assertThat(response.getContentAsString())
        .contains(TestFixtures.EXPECTED_APPLICATION_HEALTH_STATUS_KEY);

    assertThat(response.getContentAsString())
        .contains(TestFixtures.EXPECTED_APPLICATION_HEALTH_STATUS_VALUE);
  }
}
