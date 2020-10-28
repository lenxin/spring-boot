package org.springframework.boot.test.mock.mockito;

import org.springframework.boot.test.mock.mockito.example.ExampleServiceCaller;
import org.springframework.boot.test.mock.mockito.example.SimpleExampleService;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

/**
 * Config for {@link SpyBeanOnTestFieldForExistingBeanIntegrationTests} and
 * {@link SpyBeanOnTestFieldForExistingBeanCacheIntegrationTests}. Extracted to a shared
 * config to trigger caching.
 *

 */
@Configuration(proxyBeanMethods = false)
@Import({ ExampleServiceCaller.class, SimpleExampleService.class })
public class SpyBeanOnTestFieldForExistingBeanConfig {

}
