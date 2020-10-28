package org.springframework.boot.test.autoconfigure;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.test.context.TestConfiguration;

/**
 * Example {@link TestConfiguration @TestConfiguration} for
 * {@link OverrideAutoConfiguration @OverrideAutoConfiguration} tests.
 *

 */
@TestConfiguration(proxyBeanMethods = false)
@EntityScan("some.other.package")
public class ExampleTestConfig {

}
