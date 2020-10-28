package org.springframework.boot.actuate.autoconfigure.metrics.export.influx;

import io.micrometer.influx.InfluxConfig;
import org.junit.jupiter.api.Test;

import org.springframework.boot.actuate.autoconfigure.metrics.export.properties.StepRegistryPropertiesTests;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Tests for {@link InfluxProperties}.
 *

 */
class InfluxPropertiesTests extends StepRegistryPropertiesTests {

	@Test
	void defaultValuesAreConsistent() {
		InfluxProperties properties = new InfluxProperties();
		InfluxConfig config = InfluxConfig.DEFAULT;
		assertStepRegistryDefaultValues(properties, config);
		assertThat(properties.getDb()).isEqualTo(config.db());
		assertThat(properties.getConsistency()).isEqualTo(config.consistency());
		assertThat(properties.getUserName()).isEqualTo(config.userName());
		assertThat(properties.getPassword()).isEqualTo(config.password());
		assertThat(properties.getRetentionPolicy()).isEqualTo(config.retentionPolicy());
		assertThat(properties.getRetentionDuration()).isEqualTo(config.retentionDuration());
		assertThat(properties.getRetentionReplicationFactor()).isEqualTo(config.retentionReplicationFactor());
		assertThat(properties.getRetentionShardDuration()).isEqualTo(config.retentionShardDuration());
		assertThat(properties.getUri()).isEqualTo(config.uri());
		assertThat(properties.isCompressed()).isEqualTo(config.compressed());
		assertThat(properties.isAutoCreateDb()).isEqualTo(config.autoCreateDb());
	}

}
