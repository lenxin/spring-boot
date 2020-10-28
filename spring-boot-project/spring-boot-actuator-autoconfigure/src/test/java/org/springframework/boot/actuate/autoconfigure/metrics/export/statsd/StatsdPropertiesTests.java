package org.springframework.boot.actuate.autoconfigure.metrics.export.statsd;

import io.micrometer.statsd.StatsdConfig;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Tests for {@link StatsdProperties}.
 *

 */
class StatsdPropertiesTests {

	@Test
	void defaultValuesAreConsistent() {
		StatsdProperties properties = new StatsdProperties();
		StatsdConfig config = StatsdConfig.DEFAULT;
		assertThat(properties.isEnabled()).isEqualTo(config.enabled());
		assertThat(properties.getFlavor()).isEqualTo(config.flavor());
		assertThat(properties.getHost()).isEqualTo(config.host());
		assertThat(properties.getPort()).isEqualTo(config.port());
		assertThat(properties.getProtocol()).isEqualTo(config.protocol());
		assertThat(properties.getMaxPacketLength()).isEqualTo(config.maxPacketLength());
		assertThat(properties.getPollingFrequency()).isEqualTo(config.pollingFrequency());
		assertThat(properties.isPublishUnchangedMeters()).isEqualTo(config.publishUnchangedMeters());
	}

}
