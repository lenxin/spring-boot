package org.springframework.boot.autoconfigure.validation;

import javax.validation.Validator;

import org.junit.jupiter.api.Test;

import org.springframework.boot.autoconfigure.AutoConfigurations;
import org.springframework.boot.test.context.runner.ApplicationContextRunner;
import org.springframework.boot.testsupport.classpath.ClassPathExclusions;
import org.springframework.validation.beanvalidation.MethodValidationPostProcessor;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Test for {@link ValidationAutoConfiguration} when Hibernate validator is present but no
 * EL implementation is available.
 *

 */
@ClassPathExclusions({ "tomcat-embed-el-*.jar", "el-api-*.jar" })
class ValidationAutoConfigurationWithHibernateValidatorMissingElImplTests {

	private final ApplicationContextRunner contextRunner = new ApplicationContextRunner()
			.withConfiguration(AutoConfigurations.of(ValidationAutoConfiguration.class));

	@Test
	void missingElDependencyIsTolerated() {
		this.contextRunner.run((context) -> {
			assertThat(context).hasSingleBean(Validator.class);
			assertThat(context).hasSingleBean(MethodValidationPostProcessor.class);
		});
	}

}
