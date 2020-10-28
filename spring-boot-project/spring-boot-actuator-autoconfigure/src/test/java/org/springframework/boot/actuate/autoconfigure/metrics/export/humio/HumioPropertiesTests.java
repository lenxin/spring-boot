package org.springframework.boot.actuate.autoconfigure.metrics.export.humio;

import io.micrometer.humio.HumioConfig;
import org.junit.jupiter.api.Test;

import org.springframework.boot.actuate.autoconfigure.metrics.export.properties.StepRegistryPropertiesTests;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Tests for {@link HumioProperties}.
 *

 */
class HumioPropertiesTests extends StepRegistryPropertiesTests {

	@Test
	void defaultValuesAreConsistent() {
		HumioProperties properties = new HumioProperties();
		HumioConfig config = (key) -> null;
		assertStepRegistryDefaultValues(properties, config);
		assertThat(properties.getApiToken()).isEqualTo(config.apiToken());
		assertThat(properties.getTags()).isEmpty();
		assertThat(config.tags()).isNull();
		assertThat(properties.getUri()).isEqualTo(config.uri());
	}

}
