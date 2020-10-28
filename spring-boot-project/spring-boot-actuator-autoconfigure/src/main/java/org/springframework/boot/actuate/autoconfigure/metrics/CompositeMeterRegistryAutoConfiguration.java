package org.springframework.boot.actuate.autoconfigure.metrics;

import io.micrometer.core.instrument.composite.CompositeMeterRegistry;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

/**
 * {@link EnableAutoConfiguration Auto-configuration} for a
 * {@link CompositeMeterRegistry}.
 *

 * @since 2.0.0
 */
@Configuration(proxyBeanMethods = false)
@Import({ NoOpMeterRegistryConfiguration.class, CompositeMeterRegistryConfiguration.class })
@ConditionalOnClass(CompositeMeterRegistry.class)
public class CompositeMeterRegistryAutoConfiguration {

}
