package org.springframework.boot.autoconfigure;

import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.support.BeanNameGenerator;
import org.springframework.beans.factory.support.DefaultBeanNameGenerator;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.AnnotatedElementUtils;
import org.springframework.core.annotation.AnnotationAttributes;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Tests for {@link SpringBootApplication @SpringBootApplication}.
 *


 */
class SpringBootApplicationTests {

	@Test
	void proxyBeanMethodsIsEnabledByDefault() {
		AnnotationAttributes attributes = AnnotatedElementUtils
				.getMergedAnnotationAttributes(DefaultSpringBootApplication.class, Configuration.class);
		assertThat(attributes.get("proxyBeanMethods")).isEqualTo(true);
	}

	@Test
	void proxyBeanMethodsCanBeDisabled() {
		AnnotationAttributes attributes = AnnotatedElementUtils
				.getMergedAnnotationAttributes(NoBeanMethodProxyingSpringBootApplication.class, Configuration.class);
		assertThat(attributes.get("proxyBeanMethods")).isEqualTo(false);
	}

	@Test
	void nameGeneratorDefaultToBeanNameGenerator() {
		AnnotationAttributes attributes = AnnotatedElementUtils
				.getMergedAnnotationAttributes(DefaultSpringBootApplication.class, ComponentScan.class);
		assertThat(attributes.get("nameGenerator")).isEqualTo(BeanNameGenerator.class);
	}

	@Test
	void nameGeneratorCanBeSpecified() {
		AnnotationAttributes attributes = AnnotatedElementUtils
				.getMergedAnnotationAttributes(CustomNameGeneratorConfiguration.class, ComponentScan.class);
		assertThat(attributes.get("nameGenerator")).isEqualTo(TestBeanNameGenerator.class);
	}

	@SpringBootApplication
	static class DefaultSpringBootApplication {

	}

	@SpringBootApplication(proxyBeanMethods = false)
	static class NoBeanMethodProxyingSpringBootApplication {

	}

	@SpringBootApplication(nameGenerator = TestBeanNameGenerator.class)
	static class CustomNameGeneratorConfiguration {

	}

	static class TestBeanNameGenerator extends DefaultBeanNameGenerator {

	}

}
