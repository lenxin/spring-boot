package org.springframework.boot.actuate.autoconfigure.metrics;

import io.micrometer.core.instrument.Clock;
import io.micrometer.newrelic.NewRelicMeterRegistry;
import org.junit.jupiter.api.Test;

import org.springframework.boot.actuate.autoconfigure.metrics.export.newrelic.NewRelicProperties;
import org.springframework.boot.actuate.autoconfigure.metrics.export.newrelic.NewRelicPropertiesConfigAdapter;
import org.springframework.boot.diagnostics.FailureAnalysis;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.fail;

/**
 * Tests for {@link ValidationFailureAnalyzer}.
 *

 */
class ValidationFailureAnalyzerTests {

	@Test
	void analyzesMissingRequiredConfiguration() {
		FailureAnalysis analysis = new ValidationFailureAnalyzer()
				.analyze(createFailure(MissingAccountIdAndApiKeyConfiguration.class));
		assertThat(analysis).isNotNull();
		assertThat(analysis.getCause().getMessage()).contains("management.metrics.export.newrelic.apiKey was 'null'");
		assertThat(analysis.getDescription()).isEqualTo(String.format("Invalid Micrometer configuration detected:%n%n"
				+ "  - management.metrics.export.newrelic.apiKey was 'null' but it is required when publishing to Insights API%n"
				+ "  - management.metrics.export.newrelic.accountId was 'null' but it is required when publishing to Insights API"));
	}

	private Exception createFailure(Class<?> configuration) {
		try (ConfigurableApplicationContext context = new AnnotationConfigApplicationContext(configuration)) {
			fail("Expected failure did not occur");
			return null;
		}
		catch (Exception ex) {
			return ex;
		}
	}

	@Configuration(proxyBeanMethods = false)
	@Import(NewRelicProperties.class)
	static class MissingAccountIdAndApiKeyConfiguration {

		@Bean
		NewRelicMeterRegistry meterRegistry(NewRelicProperties newRelicProperties) {
			return new NewRelicMeterRegistry(new NewRelicPropertiesConfigAdapter(newRelicProperties), Clock.SYSTEM);
		}

	}

}
