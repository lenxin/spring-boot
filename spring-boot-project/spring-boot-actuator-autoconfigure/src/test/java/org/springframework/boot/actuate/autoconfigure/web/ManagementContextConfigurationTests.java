package org.springframework.boot.actuate.autoconfigure.web;

import org.junit.jupiter.api.Test;

import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.AnnotatedElementUtils;
import org.springframework.core.annotation.AnnotationAttributes;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Tests for {@link ManagementContextConfiguration @ManagementContextConfiguration}.
 *

 */
class ManagementContextConfigurationTests {

	@Test
	void proxyBeanMethodsIsEnabledByDefault() {
		AnnotationAttributes attributes = AnnotatedElementUtils
				.getMergedAnnotationAttributes(DefaultManagementContextConfiguration.class, Configuration.class);
		assertThat(attributes.get("proxyBeanMethods")).isEqualTo(true);
	}

	@Test
	void proxyBeanMethodsCanBeDisabled() {
		AnnotationAttributes attributes = AnnotatedElementUtils.getMergedAnnotationAttributes(
				NoBeanMethodProxyingManagementContextConfiguration.class, Configuration.class);
		assertThat(attributes.get("proxyBeanMethods")).isEqualTo(false);
	}

	@ManagementContextConfiguration
	static class DefaultManagementContextConfiguration {

	}

	@ManagementContextConfiguration(proxyBeanMethods = false)
	static class NoBeanMethodProxyingManagementContextConfiguration {

	}

}
