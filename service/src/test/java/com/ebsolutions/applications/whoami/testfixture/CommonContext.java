package com.ebsolutions.applications.whoami.testfixture;

import org.springframework.context.annotation.Import;

@Import({
    CommonContextConfig.class
})
public class CommonContext implements HttpTestFixturesContext, TestFixturesContext {
}