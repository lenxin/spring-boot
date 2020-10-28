package org.springframework.boot.actuate.autoconfigure.metrics;

import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.binder.jvm.ClassLoaderMetrics;
import io.micrometer.core.instrument.binder.jvm.JvmGcMetrics;
import io.micrometer.core.instrument.binder.jvm.JvmMemoryMetrics;
import io.micrometer.core.instrument.binder.jvm.JvmThreadMetrics;

import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * {@link EnableAutoConfiguration Auto-configuration} for JVM metrics.
 *

 * @since 2.1.0
 */
@Configuration(proxyBeanMethods = false)
@AutoConfigureAfter({ MetricsAutoConfiguration.class, CompositeMeterRegistryAutoConfiguration.class })
@ConditionalOnClass(MeterRegistry.class)
@ConditionalOnBean(MeterRegistry.class)
public class JvmMetricsAutoConfiguration {

	@Bean
	@ConditionalOnMissingBean
	public JvmGcMetrics jvmGcMetrics() {
		return new JvmGcMetrics();
	}

	@Bean
	@ConditionalOnMissingBean
	public JvmMemoryMetrics jvmMemoryMetrics() {
		return new JvmMemoryMetrics();
	}

	@Bean
	@ConditionalOnMissingBean
	public JvmThreadMetrics jvmThreadMetrics() {
		return new JvmThreadMetrics();
	}

	@Bean
	@ConditionalOnMissingBean
	public ClassLoaderMetrics classLoaderMetrics() {
		return new ClassLoaderMetrics();
	}

}
