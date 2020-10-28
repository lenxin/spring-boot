package org.springframework.boot;

import org.junit.jupiter.api.Test;

import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.AnnotatedElementUtils;
import org.springframework.core.annotation.AnnotationAttributes;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Tests for {@link SpringBootConfiguration @SpringBootConfiguration}.
 *

 */
class SpringBootConfigurationTests {

	@Test
	void proxyBeanMethodsIsEnabledByDefault() {
		AnnotationAttributes attributes = AnnotatedElementUtils
				.getMergedAnnotationAttributes(DefaultSpringBootConfiguration.class, Configuration.class);
		assertThat(attributes.get("proxyBeanMethods")).isEqualTo(true);
	}

	@Test
	void proxyBeanMethodsCanBeDisabled() {
		AnnotationAttributes attributes = AnnotatedElementUtils
				.getMergedAnnotationAttributes(NoBeanMethodProxyingSpringBootConfiguration.class, Configuration.class);
		assertThat(attributes.get("proxyBeanMethods")).isEqualTo(false);
	}

	@SpringBootConfiguration
	static class DefaultSpringBootConfiguration {

	}

	@SpringBootConfiguration(proxyBeanMethods = false)
	static class NoBeanMethodProxyingSpringBootConfiguration {

	}

}
