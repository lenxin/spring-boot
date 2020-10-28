package org.springframework.boot.actuate.autoconfigure.metrics.export.ganglia;

import io.micrometer.ganglia.GangliaConfig;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Tests for {@link GangliaProperties}.
 *

 */
class GangliaPropertiesTests {

	@Test
	@SuppressWarnings("deprecation")
	void defaultValuesAreConsistent() {
		GangliaProperties properties = new GangliaProperties();
		GangliaConfig config = GangliaConfig.DEFAULT;
		assertThat(properties.isEnabled()).isEqualTo(config.enabled());
		assertThat(properties.getStep()).isEqualTo(config.step());
		assertThat(properties.getRateUnits()).isEqualTo(config.rateUnits());
		assertThat(properties.getDurationUnits()).isEqualTo(config.durationUnits());
		assertThat(properties.getProtocolVersion()).isEqualTo(config.protocolVersion());
		assertThat(properties.getAddressingMode()).isEqualTo(config.addressingMode());
		assertThat(properties.getTimeToLive()).isEqualTo(config.ttl());
		assertThat(properties.getHost()).isEqualTo(config.host());
		assertThat(properties.getPort()).isEqualTo(config.port());
	}

}
