package org.springframework.boot.test.autoconfigure.filter;

import org.junit.jupiter.api.Test;

import org.springframework.boot.context.TypeExcludeFilter;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.core.type.classreading.MetadataReader;
import org.springframework.core.type.classreading.MetadataReaderFactory;
import org.springframework.core.type.classreading.SimpleMetadataReaderFactory;
import org.springframework.test.context.ContextCustomizer;
import org.springframework.test.context.MergedContextConfiguration;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;

/**
 * Tests for {@link TypeExcludeFiltersContextCustomizerFactory}.
 *

 */
class TypeExcludeFiltersContextCustomizerFactoryTests {

	private TypeExcludeFiltersContextCustomizerFactory factory = new TypeExcludeFiltersContextCustomizerFactory();

	private MergedContextConfiguration mergedContextConfiguration = mock(MergedContextConfiguration.class);

	private ConfigurableApplicationContext context = new AnnotationConfigApplicationContext();

	@Test
	void getContextCustomizerWhenHasNoAnnotationShouldReturnNull() {
		ContextCustomizer customizer = this.factory.createContextCustomizer(NoAnnotation.class, null);
		assertThat(customizer).isNull();
	}

	@Test
	void getContextCustomizerWhenHasAnnotationShouldReturnCustomizer() {
		ContextCustomizer customizer = this.factory.createContextCustomizer(WithExcludeFilters.class, null);
		assertThat(customizer).isNotNull();
	}

	@Test
	void hashCodeAndEquals() {
		ContextCustomizer customizer1 = this.factory.createContextCustomizer(WithExcludeFilters.class, null);
		ContextCustomizer customizer2 = this.factory.createContextCustomizer(WithSameExcludeFilters.class, null);
		ContextCustomizer customizer3 = this.factory.createContextCustomizer(WithDifferentExcludeFilters.class, null);
		assertThat(customizer1.hashCode()).isEqualTo(customizer2.hashCode());
		assertThat(customizer1).isEqualTo(customizer1).isEqualTo(customizer2).isNotEqualTo(customizer3);
	}

	@Test
	void getContextCustomizerShouldAddExcludeFilters() throws Exception {
		ContextCustomizer customizer = this.factory.createContextCustomizer(WithExcludeFilters.class, null);
		customizer.customizeContext(this.context, this.mergedContextConfiguration);
		this.context.refresh();
		TypeExcludeFilter filter = this.context.getBean(TypeExcludeFilter.class);
		MetadataReaderFactory metadataReaderFactory = new SimpleMetadataReaderFactory();
		MetadataReader metadataReader = metadataReaderFactory.getMetadataReader(NoAnnotation.class.getName());
		assertThat(filter.match(metadataReader, metadataReaderFactory)).isFalse();
		metadataReader = metadataReaderFactory.getMetadataReader(SimpleExclude.class.getName());
		assertThat(filter.match(metadataReader, metadataReaderFactory)).isTrue();
		metadataReader = metadataReaderFactory.getMetadataReader(TestClassAwareExclude.class.getName());
		assertThat(filter.match(metadataReader, metadataReaderFactory)).isTrue();
	}

	static class NoAnnotation {

	}

	@TypeExcludeFilters({ SimpleExclude.class, TestClassAwareExclude.class })
	static class WithExcludeFilters {

	}

	@TypeExcludeFilters({ TestClassAwareExclude.class, SimpleExclude.class })
	static class WithSameExcludeFilters {

	}

	@TypeExcludeFilters(SimpleExclude.class)
	static class WithDifferentExcludeFilters {

	}

	static class SimpleExclude extends TypeExcludeFilter {

		@Override
		public boolean match(MetadataReader metadataReader, MetadataReaderFactory metadataReaderFactory) {
			return metadataReader.getClassMetadata().getClassName().equals(getClass().getName());
		}

		@Override
		public boolean equals(Object obj) {
			return obj.getClass() == getClass();
		}

		@Override
		public int hashCode() {
			return SimpleExclude.class.hashCode();
		}

	}

	static class TestClassAwareExclude extends SimpleExclude {

		TestClassAwareExclude(Class<?> testClass) {
			assertThat(testClass).isNotNull();
		}

	}

}
