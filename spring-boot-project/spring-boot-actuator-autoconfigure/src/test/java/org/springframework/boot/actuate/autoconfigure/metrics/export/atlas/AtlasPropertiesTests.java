package org.springframework.boot.actuate.autoconfigure.metrics.export.atlas;

import com.netflix.spectator.atlas.AtlasConfig;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Tests for {@link AtlasProperties}.
 *

 */
class AtlasPropertiesTests {

	@Test
	void defaultValuesAreConsistent() {
		AtlasProperties properties = new AtlasProperties();
		AtlasConfig config = (key) -> null;
		assertThat(properties.getStep()).isEqualTo(config.step());
		assertThat(properties.isEnabled()).isEqualTo(config.enabled());
		assertThat(properties.getConnectTimeout()).isEqualTo(config.connectTimeout());
		assertThat(properties.getReadTimeout()).isEqualTo(config.readTimeout());
		assertThat(properties.getNumThreads()).isEqualTo(config.numThreads());
		assertThat(properties.getBatchSize()).isEqualTo(config.batchSize());
		assertThat(properties.getUri()).isEqualTo(config.uri());
		assertThat(properties.getMeterTimeToLive()).isEqualTo(config.meterTTL());
		assertThat(properties.isLwcEnabled()).isEqualTo(config.lwcEnabled());
		assertThat(properties.getConfigRefreshFrequency()).isEqualTo(config.configRefreshFrequency());
		assertThat(properties.getConfigTimeToLive()).isEqualTo(config.configTTL());
		assertThat(properties.getConfigUri()).isEqualTo(config.configUri());
		assertThat(properties.getEvalUri()).isEqualTo(config.evalUri());
	}

}
