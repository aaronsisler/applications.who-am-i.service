package com.ebsolutions.applications.whoami.tooling;

import com.ebsolutions.applications.whoami.appuser.core.AppUserRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.cucumber.spring.CucumberContextConfiguration;
import java.util.HashMap;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

@SpringBootTest
@AutoConfigureMockMvc
@CucumberContextConfiguration
//@ActiveProfiles("test")
public class BaseTest {
  protected final ObjectMapper objectMapper = new ObjectMapper()
      .findAndRegisterModules();

  @Autowired
  protected MockMvc mockMvc;

  @Autowired
  protected AppUserRepository appUserRepository;

  // Store the request payload for the scenario
  protected Map<String, Object> requestPayload = new HashMap<>();

  // Store the latest response for the scenario
  protected MvcResult latestResponse;
}
