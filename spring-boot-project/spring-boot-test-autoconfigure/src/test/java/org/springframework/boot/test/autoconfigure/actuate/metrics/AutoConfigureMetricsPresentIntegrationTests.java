package org.springframework.boot.test.autoconfigure.actuate.metrics;

import io.micrometer.prometheus.PrometheusMeterRegistry;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.core.env.Environment;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Integration test to verify behaviour when
 * {@link AutoConfigureMetrics @AutoConfigureMetrics} is present on the test class.
 *

 */
@SpringBootTest
@AutoConfigureMetrics
class AutoConfigureMetricsPresentIntegrationTests {

	@Test
	void customizerDoesNotDisableAvailableMeterRegistriesWhenAnnotationPresent(
			@Autowired ApplicationContext applicationContext) {
		assertThat(applicationContext.getBeansOfType(PrometheusMeterRegistry.class)).hasSize(1);
	}

	@Test
	void customizerDoesNotSetExclusionPropertiesWhenAnnotationPresent(@Autowired Environment environment) {
		assertThat(environment.containsProperty("management.metrics.export.enabled")).isFalse();
		assertThat(environment.containsProperty("management.metrics.export.simple.enabled")).isFalse();
	}

}
