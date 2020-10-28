package org.springframework.boot.actuate.autoconfigure.metrics;

import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.binder.system.FileDescriptorMetrics;
import io.micrometer.core.instrument.binder.system.ProcessorMetrics;
import io.micrometer.core.instrument.binder.system.UptimeMetrics;

import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * {@link EnableAutoConfiguration Auto-configuration} for system metrics.
 *

 * @since 2.1.0
 */
@Configuration(proxyBeanMethods = false)
@AutoConfigureAfter({ MetricsAutoConfiguration.class, CompositeMeterRegistryAutoConfiguration.class })
@ConditionalOnClass(MeterRegistry.class)
@ConditionalOnBean(MeterRegistry.class)
public class SystemMetricsAutoConfiguration {

	@Bean
	@ConditionalOnMissingBean
	public UptimeMetrics uptimeMetrics() {
		return new UptimeMetrics();
	}

	@Bean
	@ConditionalOnMissingBean
	public ProcessorMetrics processorMetrics() {
		return new ProcessorMetrics();
	}

	@Bean
	@ConditionalOnMissingBean
	public FileDescriptorMetrics fileDescriptorMetrics() {
		return new FileDescriptorMetrics();
	}

}
