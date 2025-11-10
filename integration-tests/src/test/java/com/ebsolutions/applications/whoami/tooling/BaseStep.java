package com.ebsolutions.applications.whoami.tooling;

import com.ebsolutions.applications.whoami.config.TestConstants;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.Map;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.web.client.RestClient;

public class BaseStep {
  protected final ObjectMapper objectMapper = new ObjectMapper().findAndRegisterModules();

  protected final ParameterizedTypeReference<Map<String, Object>> mapTypeRef =
      new ParameterizedTypeReference<>() {
      };

  protected RestClient restClient = RestClient
      .builder()
      .baseUrl(TestConstants.BASE_URL)
      .build();

}
