package org.springframework.boot.test.mock.mockito;

import org.springframework.boot.test.mock.mockito.example.ExampleServiceCaller;
import org.springframework.boot.test.mock.mockito.example.FailingExampleService;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

/**
 * Config for {@link MockBeanOnTestFieldForExistingBeanIntegrationTests} and
 * {@link MockBeanOnTestFieldForExistingBeanCacheIntegrationTests}. Extracted to a shared
 * config to trigger caching.
 *

 */
@Configuration(proxyBeanMethods = false)
@Import({ ExampleServiceCaller.class, FailingExampleService.class })
public class MockBeanOnTestFieldForExistingBeanConfig {

}
