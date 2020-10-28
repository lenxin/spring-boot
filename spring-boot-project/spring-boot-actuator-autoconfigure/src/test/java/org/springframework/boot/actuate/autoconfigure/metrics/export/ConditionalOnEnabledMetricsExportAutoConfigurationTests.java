package org.springframework.boot.actuate.autoconfigure.metrics.export;

import org.junit.jupiter.api.Test;

import org.springframework.boot.actuate.autoconfigure.metrics.test.MetricsRun;
import org.springframework.boot.test.context.runner.ApplicationContextRunner;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Tests for {@link ConditionalOnEnabledMetricsExport}.
 *

 */
class ConditionalOnEnabledMetricsExportAutoConfigurationTests {

	private final ApplicationContextRunner contextRunner = new ApplicationContextRunner().with(MetricsRun.simple());

	@Test
	void exporterIsEnabledByDefault() {
		this.contextRunner.run((context) -> assertThat(context).hasBean("simpleMeterRegistry"));
	}

	@Test
	void exporterCanBeSpecificallyDisabled() {
		this.contextRunner.withPropertyValues("management.metrics.export.simple.enabled=false")
				.run((context) -> assertThat(context).doesNotHaveBean("simpleMeterRegistry"));
	}

	@Test
	void exporterCanBeGloballyDisabled() {
		this.contextRunner.withPropertyValues("management.metrics.export.defaults.enabled=false")
				.run((context) -> assertThat(context).doesNotHaveBean("simpleMeterRegistry"));
	}

	@Test
	void exporterCanBeGloballyDisabledWitSpecificOverride() {
		this.contextRunner
				.withPropertyValues("management.metrics.export.defaults.enabled=false",
						"management.metrics.export.simple.enabled=true")
				.run((context) -> assertThat(context).hasBean("simpleMeterRegistry"));
	}

}
