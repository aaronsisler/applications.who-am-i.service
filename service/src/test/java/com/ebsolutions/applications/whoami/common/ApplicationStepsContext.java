package com.ebsolutions.applications.whoami.common;

import com.ebsolutions.applications.whoami.appuser.core.AppUserRepository;
import io.cucumber.spring.CucumberContextConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;

@Import({
    ApplicationContextConfig.class
})
@CucumberContextConfiguration
@SpringBootTest
public class ApplicationStepsContext extends BaseStepsContext {

  @Autowired
  public AppUserRepository appUserRepository;

}