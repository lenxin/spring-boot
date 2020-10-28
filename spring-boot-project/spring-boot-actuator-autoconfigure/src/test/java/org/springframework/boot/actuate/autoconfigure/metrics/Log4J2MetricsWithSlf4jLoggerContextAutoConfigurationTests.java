package org.springframework.boot.actuate.autoconfigure.metrics;

import io.micrometer.core.instrument.binder.logging.Log4j2Metrics;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.slf4j.SLF4JLoggerContext;
import org.junit.jupiter.api.Test;

import org.springframework.boot.actuate.autoconfigure.metrics.test.MetricsRun;
import org.springframework.boot.autoconfigure.AutoConfigurations;
import org.springframework.boot.test.context.runner.ApplicationContextRunner;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Tests for {@link Log4J2MetricsAutoConfiguration}.
 *

 */
class Log4J2MetricsWithSlf4jLoggerContextAutoConfigurationTests {

	private final ApplicationContextRunner contextRunner = new ApplicationContextRunner().with(MetricsRun.simple())
			.withConfiguration(AutoConfigurations.of(Log4J2MetricsAutoConfiguration.class));

	@Test
	void backsOffWhenLoggerContextIsBackedBySlf4j() {
		assertThat(LogManager.getContext()).isInstanceOf(SLF4JLoggerContext.class);
		this.contextRunner.run((context) -> assertThat(context).doesNotHaveBean(Log4j2Metrics.class));
	}

}
