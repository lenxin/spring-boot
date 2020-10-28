package org.springframework.boot.actuate.autoconfigure.metrics;

import io.micrometer.core.instrument.binder.logging.Log4j2Metrics;
import org.apache.logging.log4j.LogManager;
import org.junit.jupiter.api.Test;

import org.springframework.boot.actuate.autoconfigure.metrics.test.MetricsRun;
import org.springframework.boot.autoconfigure.AutoConfigurations;
import org.springframework.boot.test.context.runner.ApplicationContextRunner;
import org.springframework.boot.testsupport.classpath.ClassPathOverrides;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Tests for {@link Log4J2MetricsAutoConfiguration}.
 *

 */
@ClassPathOverrides("org.apache.logging.log4j:log4j-core:2.11.1")
class Log4J2MetricsWithLog4jLoggerContextAutoConfigurationTests {

	private final ApplicationContextRunner contextRunner = new ApplicationContextRunner().with(MetricsRun.simple())
			.withConfiguration(AutoConfigurations.of(Log4J2MetricsAutoConfiguration.class));

	@Test
	void autoConfiguresLog4J2Metrics() {
		assertThat(LogManager.getContext().getClass().getName())
				.isEqualTo("org.apache.logging.log4j.core.LoggerContext");
		this.contextRunner.run((context) -> assertThat(context).hasSingleBean(Log4j2Metrics.class));
	}

	@Test
	void allowsCustomLog4J2MetricsToBeUsed() {
		assertThat(LogManager.getContext().getClass().getName())
				.isEqualTo("org.apache.logging.log4j.core.LoggerContext");
		this.contextRunner.withUserConfiguration(CustomLog4J2MetricsConfiguration.class).run(
				(context) -> assertThat(context).hasSingleBean(Log4j2Metrics.class).hasBean("customLog4J2Metrics"));
	}

	@Configuration(proxyBeanMethods = false)
	static class CustomLog4J2MetricsConfiguration {

		@Bean
		Log4j2Metrics customLog4J2Metrics() {
			return new Log4j2Metrics();
		}

	}

}
