package org.springframework.boot.docs.actuate.metrics;

import io.micrometer.core.instrument.config.MeterFilter;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Example to show a {@link MeterFilter}.
 *

 */
public class MetricsFilterBeanExample {

	@Configuration(proxyBeanMethods = false)
	public static class MetricsFilterExampleConfiguration {

		// tag::configuration[]
		@Bean
		public MeterFilter renameRegionTagMeterFilter() {
			return MeterFilter.renameTag("com.example", "mytag.region", "mytag.area");
		}
		// end::configuration[]

	}

}
