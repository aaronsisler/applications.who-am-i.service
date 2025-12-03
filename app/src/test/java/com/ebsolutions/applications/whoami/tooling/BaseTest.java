package com.ebsolutions.applications.whoami.tooling;

import com.ebsolutions.applications.whoami.appuser.core.AppUserRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import io.cucumber.spring.CucumberContextConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc
@CucumberContextConfiguration
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.ANY)
public class BaseTest {
  protected final ObjectMapper objectMapper = new ObjectMapper()
      .findAndRegisterModules()
      // Needed to allow for empty file to be sent
      .configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);

  @Autowired
  protected MockMvc mockMvc;

  @Autowired
  private AppUserRepository appUserRepository;
}
