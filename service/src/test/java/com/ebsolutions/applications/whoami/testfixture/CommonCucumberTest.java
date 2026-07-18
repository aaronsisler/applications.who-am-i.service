package com.ebsolutions.applications.whoami.testfixture;

import io.cucumber.spring.CucumberContextConfiguration;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import org.springframework.boot.test.context.SpringBootTest;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@CucumberContextConfiguration
@SpringBootTest(
    classes = {com.ebsolutions.applications.whoami.Application.class},
    webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT
)
public @interface CommonCucumberTest {
}
