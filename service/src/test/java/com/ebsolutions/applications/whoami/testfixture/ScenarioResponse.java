package com.ebsolutions.applications.whoami.testfixture;

import jakarta.validation.constraints.Size;
import java.util.Map;

public record ScenarioResponse(
    @Size(min = 200) int statusCode,
    String body,
    Map<String, String> headers
) {
}