package org.springframework.boot.actuate.autoconfigure.metrics.export.jmx;

import io.micrometer.jmx.JmxConfig;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Tests for {@link JmxProperties}.
 *

 */
class JmxPropertiesTests {

	@Test
	void defaultValuesAreConsistent() {
		JmxProperties properties = new JmxProperties();
		JmxConfig config = JmxConfig.DEFAULT;
		assertThat(properties.getDomain()).isEqualTo(config.domain());
		assertThat(properties.getStep()).isEqualTo(config.step());
	}

}
