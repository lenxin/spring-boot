package org.springframework.boot.docs.actuate.metrics;

import io.micrometer.core.instrument.Gauge;
import io.micrometer.core.instrument.MeterRegistry;

import org.springframework.boot.actuate.health.HealthEndpoint;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.boot.actuate.health.Status;
import org.springframework.context.annotation.Configuration;

/**
 * Example to show how to export {@link HealthIndicator} beans to a {@link MeterRegistry}.
 *

 */
public class MetricsHealthMicrometerExportExample {

	// tag::configuration[]
	@Configuration
	public class HealthMetricsConfiguration {

		public HealthMetricsConfiguration(MeterRegistry registry, HealthEndpoint healthEndpoint) {
			// This example presumes common tags (such as the app) are applied elsewhere
			Gauge.builder("health", healthEndpoint, this::getStatusCode).strongReference(true).register(registry);
		}

		private int getStatusCode(HealthEndpoint health) {
			Status status = health.health().getStatus();
			if (Status.UP.equals(status)) {
				return 3;
			}
			if (Status.OUT_OF_SERVICE.equals(status)) {
				return 2;
			}
			if (Status.DOWN.equals(status)) {
				return 1;
			}
			return 0;
		}

	}
	// end::configuration[]

}
