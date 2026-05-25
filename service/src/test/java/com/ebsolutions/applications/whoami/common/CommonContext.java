package com.ebsolutions.applications.whoami.common;

import com.ebsolutions.applications.whoami.common.testfixture.HttpTestFixturesContext;
import com.ebsolutions.applications.whoami.common.testfixture.TestFixturesContext;
import org.springframework.context.annotation.Import;

@Import({
    CommonContextConfig.class
})
public class CommonContext implements HttpTestFixturesContext, TestFixturesContext {
}