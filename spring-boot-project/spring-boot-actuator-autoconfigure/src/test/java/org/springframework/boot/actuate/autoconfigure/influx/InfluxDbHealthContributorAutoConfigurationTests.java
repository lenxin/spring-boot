package org.springframework.boot.actuate.autoconfigure.influx;

import org.influxdb.InfluxDB;
import org.junit.jupiter.api.Test;

import org.springframework.boot.actuate.autoconfigure.health.HealthContributorAutoConfiguration;
import org.springframework.boot.actuate.influx.InfluxDbHealthIndicator;
import org.springframework.boot.autoconfigure.AutoConfigurations;
import org.springframework.boot.test.context.runner.ApplicationContextRunner;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;

/**
 * Tests for {@link InfluxDbHealthContributorAutoConfiguration}.
 *

 */
class InfluxDbHealthContributorAutoConfigurationTests {

	private final ApplicationContextRunner contextRunner = new ApplicationContextRunner()
			.withBean(InfluxDB.class, () -> mock(InfluxDB.class)).withConfiguration(AutoConfigurations
					.of(InfluxDbHealthContributorAutoConfiguration.class, HealthContributorAutoConfiguration.class));

	@Test
	void runShouldCreateIndicator() {
		this.contextRunner.run((context) -> assertThat(context).hasSingleBean(InfluxDbHealthIndicator.class));
	}

	@Test
	void runWhenDisabledShouldNotCreateIndicator() {
		this.contextRunner.withPropertyValues("management.health.influxdb.enabled:false")
				.run((context) -> assertThat(context).doesNotHaveBean(InfluxDbHealthIndicator.class));
	}

}
