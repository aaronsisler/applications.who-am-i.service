package com.ebsolutions.applications.whoami.integration.system;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.ebsolutions.applications.whoami.config.ApiPaths;
import com.ebsolutions.applications.whoami.integration.IntegrationStepsContext;
import io.cucumber.java.en.Given;

public class ApplicationHealthEndpointStepsContext extends IntegrationStepsContext {

  @Given("the application is running")
  public void theApplicationIsRunning() throws Exception {
    mockMvc.perform(get(ApiPaths.APPLICATION_HEALTH_PATH))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.status", is("UP")));
  }
}
