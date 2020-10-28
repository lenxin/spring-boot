package org.springframework.boot.actuate.autoconfigure.metrics.export.elastic;

import io.micrometer.elastic.ElasticConfig;
import org.junit.jupiter.api.Test;

import org.springframework.boot.actuate.autoconfigure.metrics.export.properties.StepRegistryPropertiesTests;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Tests for {@link ElasticProperties}.
 *

 */
class ElasticPropertiesTests extends StepRegistryPropertiesTests {

	@Test
	void defaultValuesAreConsistent() {
		ElasticProperties properties = new ElasticProperties();
		ElasticConfig config = ElasticConfig.DEFAULT;
		assertStepRegistryDefaultValues(properties, config);
		assertThat(properties.getHost()).isEqualTo(config.host());
		assertThat(properties.getIndex()).isEqualTo(config.index());
		assertThat(properties.getIndexDateFormat()).isEqualTo(config.indexDateFormat());
		assertThat(properties.getIndexDateSeparator()).isEqualTo(config.indexDateSeparator());
		assertThat(properties.getPassword()).isEqualTo(config.password());
		assertThat(properties.getTimestampFieldName()).isEqualTo(config.timestampFieldName());
		assertThat(properties.getUserName()).isEqualTo(config.userName());
		assertThat(properties.isAutoCreateIndex()).isEqualTo(config.autoCreateIndex());
		assertThat(properties.getPipeline()).isEqualTo(config.pipeline());
	}

}
