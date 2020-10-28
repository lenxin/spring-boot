package org.springframework.boot.actuate.autoconfigure.metrics.export.newrelic;

import io.micrometer.newrelic.NewRelicConfig;
import org.junit.jupiter.api.Test;

import org.springframework.boot.actuate.autoconfigure.metrics.export.properties.StepRegistryPropertiesTests;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Tests for {@link NewRelicProperties}.
 *

 */
class NewRelicPropertiesTests extends StepRegistryPropertiesTests {

	@Test
	void defaultValuesAreConsistent() {
		NewRelicProperties properties = new NewRelicProperties();
		NewRelicConfig config = (key) -> null;
		assertStepRegistryDefaultValues(properties, config);
		assertThat(properties.getClientProviderType()).isEqualTo(config.clientProviderType());
		// apiKey and account are mandatory
		assertThat(properties.getUri()).isEqualTo(config.uri());
		assertThat(properties.isMeterNameEventTypeEnabled()).isEqualTo(config.meterNameEventTypeEnabled());
	}

	@Test
	void eventTypeDefaultValueIsOverridden() {
		NewRelicProperties properties = new NewRelicProperties();
		NewRelicConfig config = (key) -> null;
		assertThat(properties.getEventType()).isNotEqualTo(config.eventType());
		assertThat(properties.getEventType()).isEqualTo("SpringBootSample");
		assertThat(config.eventType()).isEqualTo("MicrometerSample");
	}

}
