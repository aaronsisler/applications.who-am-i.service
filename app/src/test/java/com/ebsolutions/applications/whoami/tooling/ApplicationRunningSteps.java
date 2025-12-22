package com.ebsolutions.applications.whoami.tooling;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.ebsolutions.applications.whoami.config.UriConstants;
import io.cucumber.java.en.Given;
import org.springframework.test.web.servlet.MvcResult;

public class ApplicationRunningSteps extends BaseTest {
  protected MvcResult result;

  @Given("the application is running")
  public void theApplicationIsRunning() throws Exception {
    mockMvc.perform(get(UriConstants.HEALTH_CHECK_URI))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.status", is("UP")));
  }
}
