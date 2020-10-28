package org.springframework.boot.docs.actuate.metrics;

import io.micrometer.core.instrument.Gauge;
import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.simple.SimpleMeterRegistry;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.autoconfigure.health.HealthContributorAutoConfiguration;
import org.springframework.boot.actuate.autoconfigure.health.HealthEndpointAutoConfiguration;
import org.springframework.boot.actuate.autoconfigure.metrics.MetricsAutoConfiguration;
import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Tests for {@link MetricsHealthMicrometerExportExample}.
 *

 */
@SpringBootTest
class MetricsHealthMicrometerExportExampleTests {

	@Autowired
	private MeterRegistry registry;

	@Test
	void registryExportsHealth() throws Exception {
		Gauge gauge = this.registry.get("health").gauge();
		assertThat(gauge.value()).isEqualTo(2);
	}

	@Configuration(proxyBeanMethods = false)
	@Import(MetricsHealthMicrometerExportExample.HealthMetricsConfiguration.class)
	@ImportAutoConfiguration(classes = { HealthContributorAutoConfiguration.class, MetricsAutoConfiguration.class,
			HealthEndpointAutoConfiguration.class })
	static class Config {

		@Bean
		MetricsHealthMicrometerExportExample example() {
			return new MetricsHealthMicrometerExportExample();
		}

		@Bean
		SimpleMeterRegistry simpleMeterRegistry() {
			return new SimpleMeterRegistry();
		}

		@Bean
		HealthIndicator outOfService() {
			return () -> new Health.Builder().outOfService().build();
		}

	}

}
