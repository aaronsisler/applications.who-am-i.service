package com.ebsolutions.applications.whoami.architecture;

import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.noClasses;

import com.tngtech.archunit.core.domain.JavaClasses;
import com.tngtech.archunit.core.importer.ClassFileImporter;
import com.tngtech.archunit.core.importer.ImportOption;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import org.springframework.validation.annotation.Validated;

/**
 * Rules inside each module: every module is a hexagon. The ".." wildcards make each rule cover
 * app-user and any module added later. Rules BETWEEN modules are Spring Modulith's job.
 */
public class ArchitectureSteps {
  private static final String ROOT = "com.ebsolutions.applications.whoami";
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

  /** Covers the controllers and the generated *Api interfaces they implement (ADR 0021). */
  @Then("no web adapter class turns off Spring MVC's built-in validation")
  public void noWebAdapterClassIsValidated() {
    noClasses()
        .that()
        .resideInAPackage("..adapter.in.web..")
        .should()
        .beAnnotatedWith(Validated.class)
        .because(
            "@Validated swaps Spring MVC's built-in validation for a proxy whose errors become"
                + " 500s instead of 400s (ADR 0021)")
        .allowEmptyShould(true)
        .check(classes);
  }
}
