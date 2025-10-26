package com.ebsolutions.applications.whoami.tooling;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.web.client.RestTemplate;

public class BaseStep {
  protected final ObjectMapper objectMapper = new ObjectMapper().findAndRegisterModules();

  protected RestTemplate restTemplate = new RestTemplate();
}
