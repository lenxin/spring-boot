package org.springframework.boot.actuate.autoconfigure.metrics.export.prometheus;

import io.micrometer.prometheus.PrometheusConfig;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Tests for {@link PrometheusProperties}.
 *

 */
class PrometheusPropertiesTests {

	@Test
	void defaultValuesAreConsistent() {
		PrometheusProperties properties = new PrometheusProperties();
		PrometheusConfig config = PrometheusConfig.DEFAULT;
		assertThat(properties.isDescriptions()).isEqualTo(config.descriptions());
		assertThat(properties.getHistogramFlavor()).isEqualTo(config.histogramFlavor());
		assertThat(properties.getStep()).isEqualTo(config.step());
	}

}
