package org.springframework.boot.actuate.autoconfigure.metrics;

import io.micrometer.core.instrument.binder.logging.LogbackMetrics;
import org.junit.jupiter.api.Test;

import org.springframework.boot.autoconfigure.AutoConfigurations;
import org.springframework.boot.test.context.runner.ApplicationContextRunner;
import org.springframework.boot.testsupport.classpath.ClassPathOverrides;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Tests for {@link MetricsAutoConfiguration} when both Log4j2 and Logback are on the
 * classpath.
 *

 */
@ClassPathOverrides({ "org.apache.logging.log4j:log4j-core:2.9.0", "org.apache.logging.log4j:log4j-slf4j-impl:2.9.0" })
class MetricsAutoConfigurationWithLog4j2AndLogbackTests {

	private final ApplicationContextRunner contextRunner = new ApplicationContextRunner()
			.withConfiguration(AutoConfigurations.of(MetricsAutoConfiguration.class));

	@Test
	void doesNotConfigureLogbackMetrics() {
		this.contextRunner.run((context) -> assertThat(context).doesNotHaveBean(LogbackMetrics.class));
	}

}
