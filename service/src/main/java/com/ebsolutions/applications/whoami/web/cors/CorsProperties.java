package com.ebsolutions.applications.whoami.web.cors;

import java.util.List;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.bind.DefaultValue;

/** Origins of the web frontends allowed to call this API from a browser. */
@ConfigurationProperties("application.cors")
record CorsProperties(@DefaultValue List<String> allowedOrigins) {}
