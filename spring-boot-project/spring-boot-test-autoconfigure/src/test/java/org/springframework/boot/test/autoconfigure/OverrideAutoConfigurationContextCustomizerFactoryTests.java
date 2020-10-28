package org.springframework.boot.test.autoconfigure;

import org.junit.jupiter.api.Test;

import org.springframework.test.context.ContextCustomizer;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Tests for {@link OverrideAutoConfigurationContextCustomizerFactory}.
 *

 */
class OverrideAutoConfigurationContextCustomizerFactoryTests {

	private OverrideAutoConfigurationContextCustomizerFactory factory = new OverrideAutoConfigurationContextCustomizerFactory();

	@Test
	void getContextCustomizerWhenHasNoAnnotationShouldReturnNull() {
		ContextCustomizer customizer = this.factory.createContextCustomizer(NoAnnotation.class, null);
		assertThat(customizer).isNull();
	}

	@Test
	void getContextCustomizerWhenHasAnnotationEnabledTrueShouldReturnNull() {
		ContextCustomizer customizer = this.factory.createContextCustomizer(WithAnnotationEnabledTrue.class, null);
		assertThat(customizer).isNull();
	}

	@Test
	void getContextCustomizerWhenHasAnnotationEnabledFalseShouldReturnCustomizer() {
		ContextCustomizer customizer = this.factory.createContextCustomizer(WithAnnotationEnabledFalse.class, null);
		assertThat(customizer).isNotNull();
	}

	@Test
	void hashCodeAndEquals() {
		ContextCustomizer customizer1 = this.factory.createContextCustomizer(WithAnnotationEnabledFalse.class, null);
		ContextCustomizer customizer2 = this.factory.createContextCustomizer(WithSameAnnotation.class, null);
		assertThat(customizer1.hashCode()).isEqualTo(customizer2.hashCode());
		assertThat(customizer1).isEqualTo(customizer1).isEqualTo(customizer2);
	}

	static class NoAnnotation {

	}

	@OverrideAutoConfiguration(enabled = true)
	static class WithAnnotationEnabledTrue {

	}

	@OverrideAutoConfiguration(enabled = false)
	static class WithAnnotationEnabledFalse {

	}

	@OverrideAutoConfiguration(enabled = false)
	static class WithSameAnnotation {

	}

}
