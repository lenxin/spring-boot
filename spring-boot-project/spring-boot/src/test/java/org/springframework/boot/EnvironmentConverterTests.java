package org.springframework.boot;

import java.util.HashSet;
import java.util.Set;

import org.junit.jupiter.api.Test;

import org.springframework.core.convert.support.ConfigurableConversionService;
import org.springframework.core.env.AbstractEnvironment;
import org.springframework.core.env.PropertySource;
import org.springframework.core.env.StandardEnvironment;
import org.springframework.mock.env.MockEnvironment;
import org.springframework.web.context.support.StandardServletEnvironment;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;

/**
 * Tests for {@link EnvironmentConverter}.
 *



 */
class EnvironmentConverterTests {

	private final EnvironmentConverter environmentConverter = new EnvironmentConverter(getClass().getClassLoader());

	@Test
	void convertedEnvironmentHasSameActiveProfiles() {
		AbstractEnvironment originalEnvironment = new MockEnvironment();
		originalEnvironment.setActiveProfiles("activeProfile1", "activeProfile2");
		StandardEnvironment convertedEnvironment = this.environmentConverter
				.convertEnvironmentIfNecessary(originalEnvironment, StandardEnvironment.class);
		assertThat(convertedEnvironment.getActiveProfiles()).containsExactly("activeProfile1", "activeProfile2");
	}

	@Test
	void convertedEnvironmentHasSameConversionService() {
		AbstractEnvironment originalEnvironment = new MockEnvironment();
		ConfigurableConversionService conversionService = mock(ConfigurableConversionService.class);
		originalEnvironment.setConversionService(conversionService);
		StandardEnvironment convertedEnvironment = this.environmentConverter
				.convertEnvironmentIfNecessary(originalEnvironment, StandardEnvironment.class);
		assertThat(convertedEnvironment.getConversionService()).isEqualTo(conversionService);
	}

	@Test
	void envClassSameShouldReturnEnvironmentUnconverted() {
		StandardEnvironment standardEnvironment = new StandardEnvironment();
		StandardEnvironment convertedEnvironment = this.environmentConverter
				.convertEnvironmentIfNecessary(standardEnvironment, StandardEnvironment.class);
		assertThat(convertedEnvironment).isSameAs(standardEnvironment);
	}

	@Test
	void standardServletEnvironmentIsConverted() {
		StandardServletEnvironment standardServletEnvironment = new StandardServletEnvironment();
		StandardEnvironment convertedEnvironment = this.environmentConverter
				.convertEnvironmentIfNecessary(standardServletEnvironment, StandardEnvironment.class);
		assertThat(convertedEnvironment).isNotSameAs(standardServletEnvironment);
	}

	@Test
	void servletPropertySourcesAreNotCopiedOverIfNotWebEnvironment() {
		StandardServletEnvironment standardServletEnvironment = new StandardServletEnvironment();
		StandardEnvironment convertedEnvironment = this.environmentConverter
				.convertEnvironmentIfNecessary(standardServletEnvironment, StandardEnvironment.class);
		assertThat(convertedEnvironment).isNotSameAs(standardServletEnvironment);
		Set<String> names = new HashSet<>();
		for (PropertySource<?> propertySource : convertedEnvironment.getPropertySources()) {
			names.add(propertySource.getName());
		}
		assertThat(names).doesNotContain(StandardServletEnvironment.SERVLET_CONTEXT_PROPERTY_SOURCE_NAME,
				StandardServletEnvironment.SERVLET_CONFIG_PROPERTY_SOURCE_NAME,
				StandardServletEnvironment.JNDI_PROPERTY_SOURCE_NAME);
	}

	@Test
	void envClassSameShouldReturnEnvironmentUnconvertedEvenForWeb() {
		StandardServletEnvironment standardServletEnvironment = new StandardServletEnvironment();
		StandardEnvironment convertedEnvironment = this.environmentConverter
				.convertEnvironmentIfNecessary(standardServletEnvironment, StandardServletEnvironment.class);
		assertThat(convertedEnvironment).isSameAs(standardServletEnvironment);
	}

	@Test
	void servletPropertySourcesArePresentWhenTypeToConvertIsWeb() {
		StandardEnvironment standardEnvironment = new StandardEnvironment();
		StandardEnvironment convertedEnvironment = this.environmentConverter
				.convertEnvironmentIfNecessary(standardEnvironment, StandardServletEnvironment.class);
		assertThat(convertedEnvironment).isNotSameAs(standardEnvironment);
		Set<String> names = new HashSet<>();
		for (PropertySource<?> propertySource : convertedEnvironment.getPropertySources()) {
			names.add(propertySource.getName());
		}
		assertThat(names).contains(StandardServletEnvironment.SERVLET_CONTEXT_PROPERTY_SOURCE_NAME,
				StandardServletEnvironment.SERVLET_CONFIG_PROPERTY_SOURCE_NAME);
	}

}
