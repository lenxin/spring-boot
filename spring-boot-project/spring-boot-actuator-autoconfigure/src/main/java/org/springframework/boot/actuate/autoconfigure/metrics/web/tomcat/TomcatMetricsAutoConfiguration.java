package org.springframework.boot.actuate.autoconfigure.metrics.web.tomcat;

import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.binder.tomcat.TomcatMetrics;
import org.apache.catalina.Manager;

import org.springframework.boot.actuate.autoconfigure.metrics.CompositeMeterRegistryAutoConfiguration;
import org.springframework.boot.actuate.metrics.web.tomcat.TomcatMetricsBinder;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * {@link EnableAutoConfiguration Auto-configuration} for {@link TomcatMetrics}.
 *

 * @since 2.0.0
 */
@Configuration(proxyBeanMethods = false)
@ConditionalOnWebApplication
@ConditionalOnClass({ TomcatMetrics.class, Manager.class })
@AutoConfigureAfter(CompositeMeterRegistryAutoConfiguration.class)
public class TomcatMetricsAutoConfiguration {

	@Bean
	@ConditionalOnBean(MeterRegistry.class)
	@ConditionalOnMissingBean({ TomcatMetrics.class, TomcatMetricsBinder.class })
	public TomcatMetricsBinder tomcatMetricsBinder(MeterRegistry meterRegistry) {
		return new TomcatMetricsBinder(meterRegistry);
	}

}
