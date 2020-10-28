package org.springframework.boot.autoconfigure.validation;

import javax.validation.Validator;

import org.junit.jupiter.api.Test;

import org.springframework.boot.autoconfigure.AutoConfigurations;
import org.springframework.boot.test.context.runner.ApplicationContextRunner;
import org.springframework.boot.testsupport.classpath.ClassPathExclusions;
import org.springframework.validation.beanvalidation.MethodValidationPostProcessor;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Test for {@link ValidationAutoConfiguration} when no JSR-303 provider is available.
 *

 */
@ClassPathExclusions("hibernate-validator-*.jar")
class ValidationAutoConfigurationWithoutValidatorTests {

	private final ApplicationContextRunner contextRunner = new ApplicationContextRunner()
			.withConfiguration(AutoConfigurations.of(ValidationAutoConfiguration.class));

	@Test
	void validationIsDisabled() {
		this.contextRunner.run((context) -> {
			assertThat(context).doesNotHaveBean(Validator.class);
			assertThat(context).doesNotHaveBean(MethodValidationPostProcessor.class);
		});
	}

}
