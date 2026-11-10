package com.ebsolutions.applications.whoami.architecture;

import com.tngtech.archunit.core.domain.JavaClasses;
import com.tngtech.archunit.core.importer.ClassFileImporter;
import com.tngtech.archunit.core.importer.ImportOption;
import io.cucumber.java.en.Given;

/**
 * Rules inside each module: every module is a hexagon. The ".." wildcards make each rule cover
 * product, inventory and any module added later. Rules BETWEEN modules are Spring Modulith's job.
 */
public class ArchitectureSteps {
  private static final String ROOT = "com.example.catalog";
  private static final String[] TECHNOLOGY = {
    "org.springframework..",
    "jakarta..",
    "java.sql..",
    "javax.sql..",
    "com.fasterxml.jackson..",
    "tools.jackson.."
  };

  private static JavaClasses classes;

  @Given("the production code of the application")
  public void theProductionCode() {
    if (classes == null) {
      classes =
          new ClassFileImporter()
              .withImportOption(ImportOption.Predefined.DO_NOT_INCLUDE_TESTS)
              .importPackages(ROOT);
    }
  }
}
