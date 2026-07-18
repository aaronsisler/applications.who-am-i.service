package com.ebsolutions.applications.whoami.testfixture.port;

import com.ebsolutions.applications.whoami.appuser.core.AppUserRepository;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

public abstract class CommonPortOverrides {
  @MockitoBean
  public AppUserRepository appUserRepository;
}
