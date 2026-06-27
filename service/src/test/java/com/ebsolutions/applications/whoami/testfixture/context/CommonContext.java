package com.ebsolutions.applications.whoami.testfixture.context;

import com.ebsolutions.applications.whoami.config.CommonContextConfig;
import org.springframework.context.annotation.Import;

@Import({
    CommonContextConfig.class
})
public class CommonContext implements HttpTestFixturesContext, TestFixturesContext {
}