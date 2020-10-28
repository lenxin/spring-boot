package org.springframework.boot.test.autoconfigure.actuate.metrics;

import java.util.Collections;

import org.junit.jupiter.api.Test;

import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.GenericApplicationContext;
import org.springframework.test.context.ContextCustomizer;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Tests for {@link AutoConfigureMetrics} and
 * {@link MetricsExportContextCustomizerFactory} working together.
 *

 */
class MetricsExportContextCustomizerFactoryTests {

	private final MetricsExportContextCustomizerFactory factory = new MetricsExportContextCustomizerFactory();

	@Test
	void getContextCustomizerWhenHasNoAnnotationShouldReturnCustomizer() {
		ContextCustomizer customizer = this.factory.createContextCustomizer(NoAnnotation.class,
				Collections.emptyList());
		assertThat(customizer).isNotNull();
		ConfigurableApplicationContext context = new GenericApplicationContext();
		customizer.customizeContext(context, null);
		assertThat(context.getEnvironment().getProperty("management.metrics.export.defaults.enabled"))
				.isEqualTo("false");
		assertThat(context.getEnvironment().getProperty("management.metrics.export.simple.enabled")).isEqualTo("true");
	}

	@Test
	void getContextCustomizerWhenHasAnnotationShouldReturnNull() {
		ContextCustomizer customizer = this.factory.createContextCustomizer(WithAnnotation.class, null);
		assertThat(customizer).isNull();
	}

	@Test
	void hashCodeAndEquals() {
		ContextCustomizer customizer1 = this.factory.createContextCustomizer(NoAnnotation.class, null);
		ContextCustomizer customizer2 = this.factory.createContextCustomizer(OtherWithNoAnnotation.class, null);
		assertThat(customizer1.hashCode()).isEqualTo(customizer2.hashCode());
		assertThat(customizer1).isEqualTo(customizer1).isEqualTo(customizer2);
	}

	static class NoAnnotation {

	}

	static class OtherWithNoAnnotation {

	}

	@AutoConfigureMetrics
	static class WithAnnotation {

	}

}
