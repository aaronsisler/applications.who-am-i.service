package com.ebsolutions.applications.whoami.common;

import com.ebsolutions.applications.whoami.common.config.rest.RestApiClientConfig;
import com.ebsolutions.applications.whoami.common.config.rest.RestClientConfig;
import io.cucumber.spring.CucumberContextConfiguration;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@CucumberContextConfiguration
@SpringBootTest(
    webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT
)
@Import({
    RestClientConfig.class,
    RestApiClientConfig.class
})
public @interface CommonCucumberTest {
}
