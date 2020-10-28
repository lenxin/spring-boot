package org.springframework.boot.actuate.autoconfigure.metrics;

import io.micrometer.core.annotation.Timed;
import io.micrometer.core.instrument.Clock;
import io.micrometer.core.instrument.binder.MeterBinder;
import io.micrometer.core.instrument.config.MeterFilter;

import org.springframework.beans.factory.ObjectProvider;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;

/**
 * {@link EnableAutoConfiguration Auto-configuration} for Micrometer-based metrics.
 *


 * @since 2.0.0
 */
@Configuration(proxyBeanMethods = false)
@ConditionalOnClass(Timed.class)
@EnableConfigurationProperties(MetricsProperties.class)
@AutoConfigureBefore(CompositeMeterRegistryAutoConfiguration.class)
public class MetricsAutoConfiguration {

	@Bean
	@ConditionalOnMissingBean
	public Clock micrometerClock() {
		return Clock.SYSTEM;
	}

	@Bean
	public static MeterRegistryPostProcessor meterRegistryPostProcessor(ObjectProvider<MeterBinder> meterBinders,
			ObjectProvider<MeterFilter> meterFilters,
			ObjectProvider<MeterRegistryCustomizer<?>> meterRegistryCustomizers,
			ObjectProvider<MetricsProperties> metricsProperties, ApplicationContext applicationContext) {
		return new MeterRegistryPostProcessor(meterBinders, meterFilters, meterRegistryCustomizers, metricsProperties,
				applicationContext);
	}

	@Bean
	@Order(0)
	public PropertiesMeterFilter propertiesMeterFilter(MetricsProperties properties) {
		return new PropertiesMeterFilter(properties);
	}

}
