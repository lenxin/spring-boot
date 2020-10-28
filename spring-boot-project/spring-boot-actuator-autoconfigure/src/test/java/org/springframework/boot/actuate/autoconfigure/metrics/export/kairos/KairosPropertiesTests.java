package org.springframework.boot.actuate.autoconfigure.metrics.export.kairos;

import io.micrometer.kairos.KairosConfig;
import org.junit.jupiter.api.Test;

import org.springframework.boot.actuate.autoconfigure.metrics.export.properties.StepRegistryPropertiesTests;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Tests for {@link KairosProperties}.
 *

 */
class KairosPropertiesTests extends StepRegistryPropertiesTests {

	@Test
	void defaultValuesAreConsistent() {
		KairosProperties properties = new KairosProperties();
		KairosConfig config = KairosConfig.DEFAULT;
		assertStepRegistryDefaultValues(properties, config);
		assertThat(properties.getUri()).isEqualToIgnoringWhitespace(config.uri());
		assertThat(properties.getUserName()).isEqualToIgnoringWhitespace(config.userName());
		assertThat(properties.getPassword()).isEqualToIgnoringWhitespace(config.password());
	}

}
