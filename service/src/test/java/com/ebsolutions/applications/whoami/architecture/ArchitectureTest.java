package com.ebsolutions.applications.whoami.architecture;

import static io.cucumber.junit.platform.engine.Constants.GLUE_PROPERTY_NAME;

import org.junit.platform.suite.api.ConfigurationParameter;
import org.junit.platform.suite.api.IncludeEngines;
import org.junit.platform.suite.api.SelectPackages;
import org.junit.platform.suite.api.Suite;

/** Architecture rules: no application context, no database (ADR 0004). */
@Suite
@IncludeEngines("cucumber")
@SelectPackages("features.architecture")
@ConfigurationParameter(
    key = GLUE_PROPERTY_NAME,
    value = "com.ebsolutions.applications.whoami.architecture")
public class ArchitectureTest {}
