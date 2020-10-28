package org.springframework.boot.actuate.autoconfigure.metrics.export.datadog;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Tests for {@link DatadogPropertiesConfigAdapter}.
 *

 */
class DatadogPropertiesConfigAdapterTests {

	@Test
	void uriCanBeSet() {
		DatadogProperties properties = new DatadogProperties();
		properties.setUri("https://app.example.com/api/v1/series");
		properties.setApiKey("my-key");
		assertThat(new DatadogPropertiesConfigAdapter(properties).uri())
				.isEqualTo("https://app.example.com/api/v1/series");
	}

}
