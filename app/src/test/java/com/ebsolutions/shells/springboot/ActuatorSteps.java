package com.ebsolutions.whoami;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.ebsolutions.whoami.config.UriConstants;
import com.ebsolutions.whoami.tooling.BaseTest;
import com.jayway.jsonpath.DocumentContext;
import com.jayway.jsonpath.JsonPath;
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
  public void theCorrectInfoResponseIsReturned() throws UnsupportedEncodingException {
    MockHttpServletResponse mockHttpServletResponse = result.getResponse();

    Assertions.assertEquals(HttpStatus.OK.value(), mockHttpServletResponse.getStatus());

    String content = mockHttpServletResponse.getContentAsString();
    DocumentContext jsonBody = JsonPath.parse(content);

    Assertions.assertEquals("com.ebsolutions.whoami", jsonBody.read("$.build.group"));
    Assertions.assertEquals("who-am-i-service", jsonBody.read("$.build.artifact"));
    Assertions.assertEquals("Who Am I Service", jsonBody.read("$.build.name"));
    Assertions.assertNotNull(jsonBody.read("$.build.version"));
    Assertions.assertNotNull(jsonBody.read("$.build.time"));
  }
}
