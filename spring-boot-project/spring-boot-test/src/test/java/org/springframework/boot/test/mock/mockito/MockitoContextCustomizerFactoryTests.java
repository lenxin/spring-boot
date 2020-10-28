package org.springframework.boot.test.mock.mockito;

import org.junit.jupiter.api.Test;

import org.springframework.test.context.ContextCustomizer;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Tests for {@link MockitoContextCustomizerFactory}.
 *

 */
class MockitoContextCustomizerFactoryTests {

	private final MockitoContextCustomizerFactory factory = new MockitoContextCustomizerFactory();

	@Test
	void getContextCustomizerWithoutAnnotationReturnsCustomizer() {
		ContextCustomizer customizer = this.factory.createContextCustomizer(NoMockBeanAnnotation.class, null);
		assertThat(customizer).isNotNull();
	}

	@Test
	void getContextCustomizerWithAnnotationReturnsCustomizer() {
		ContextCustomizer customizer = this.factory.createContextCustomizer(WithMockBeanAnnotation.class, null);
		assertThat(customizer).isNotNull();
	}

	@Test
	void getContextCustomizerUsesMocksAsCacheKey() {
		ContextCustomizer customizer = this.factory.createContextCustomizer(WithMockBeanAnnotation.class, null);
		assertThat(customizer).isNotNull();
		ContextCustomizer same = this.factory.createContextCustomizer(WithSameMockBeanAnnotation.class, null);
		assertThat(customizer).isNotNull();
		ContextCustomizer different = this.factory.createContextCustomizer(WithDifferentMockBeanAnnotation.class, null);
		assertThat(different).isNotNull();
		assertThat(customizer.hashCode()).isEqualTo(same.hashCode());
		assertThat(customizer.hashCode()).isNotEqualTo(different.hashCode());
		assertThat(customizer).isEqualTo(customizer);
		assertThat(customizer).isEqualTo(same);
		assertThat(customizer).isNotEqualTo(different);
	}

	static class NoMockBeanAnnotation {

	}

	@MockBean({ Service1.class, Service2.class })
	static class WithMockBeanAnnotation {

	}

	@MockBean({ Service2.class, Service1.class })
	static class WithSameMockBeanAnnotation {

	}

	@MockBean({ Service1.class })
	static class WithDifferentMockBeanAnnotation {

	}

	interface Service1 {

	}

	interface Service2 {

	}

}
