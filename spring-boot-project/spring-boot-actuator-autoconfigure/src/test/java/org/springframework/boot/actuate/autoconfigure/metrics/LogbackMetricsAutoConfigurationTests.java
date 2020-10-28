package org.springframework.boot.actuate.autoconfigure.metrics;

import io.micrometer.core.instrument.binder.logging.LogbackMetrics;
import org.junit.jupiter.api.Test;

import org.springframework.boot.actuate.autoconfigure.metrics.test.MetricsRun;
import org.springframework.boot.autoconfigure.AutoConfigurations;
import org.springframework.boot.test.context.runner.ApplicationContextRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Tests for {@link LogbackMetricsAutoConfiguration}.
 *


 */
class LogbackMetricsAutoConfigurationTests {

	private final ApplicationContextRunner contextRunner = new ApplicationContextRunner().with(MetricsRun.simple())
			.withConfiguration(AutoConfigurations.of(LogbackMetricsAutoConfiguration.class));

	@Test
	void autoConfiguresLogbackMetrics() {
		this.contextRunner.run((context) -> assertThat(context).hasSingleBean(LogbackMetrics.class));
	}

	@Test
	void allowsCustomLogbackMetricsToBeUsed() {
		this.contextRunner.withUserConfiguration(CustomLogbackMetricsConfiguration.class).run(
				(context) -> assertThat(context).hasSingleBean(LogbackMetrics.class).hasBean("customLogbackMetrics"));
	}

	@Configuration(proxyBeanMethods = false)
	static class CustomLogbackMetricsConfiguration {

		@Bean
		LogbackMetrics customLogbackMetrics() {
			return new LogbackMetrics();
		}

	}

}
