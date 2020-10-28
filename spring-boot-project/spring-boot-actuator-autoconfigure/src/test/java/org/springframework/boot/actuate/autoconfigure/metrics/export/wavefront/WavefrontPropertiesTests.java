package org.springframework.boot.actuate.autoconfigure.metrics.export.wavefront;

import io.micrometer.wavefront.WavefrontConfig;
import org.junit.jupiter.api.Test;

import org.springframework.boot.actuate.autoconfigure.metrics.export.properties.PushRegistryPropertiesTests;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Tests for {@link WavefrontProperties}.
 *

 */
class WavefrontPropertiesTests extends PushRegistryPropertiesTests {

	@Test
	void defaultValuesAreConsistent() {
		WavefrontProperties properties = new WavefrontProperties();
		WavefrontConfig config = WavefrontConfig.DEFAULT_DIRECT;
		assertStepRegistryDefaultValues(properties, config);
		assertThat(properties.getUri().toString()).isEqualTo(config.uri());
		assertThat(properties.getGlobalPrefix()).isEqualTo(config.globalPrefix());
	}

}
