package org.springframework.boot.validation;

import javax.validation.MessageInterpolator;
import javax.validation.Validation;
import javax.validation.ValidationException;

import org.hibernate.validator.messageinterpolation.ParameterMessageInterpolator;
import org.junit.jupiter.api.Test;

import org.springframework.boot.testsupport.classpath.ClassPathExclusions;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

/**
 * Integration tests for {@link MessageInterpolatorFactory} without EL.
 *

 */
@ClassPathExclusions("tomcat-embed-el-*.jar")
class MessageInterpolatorFactoryWithoutElIntegrationTests {

	@Test
	void defaultMessageInterpolatorShouldFail() {
		// Sanity test
		assertThatExceptionOfType(ValidationException.class)
				.isThrownBy(Validation.byDefaultProvider().configure()::getDefaultMessageInterpolator)
				.withMessageContaining("javax.el.ExpressionFactory");
	}

	@Test
	void getObjectShouldUseFallback() {
		MessageInterpolator interpolator = new MessageInterpolatorFactory().getObject();
		assertThat(interpolator).isInstanceOf(ParameterMessageInterpolator.class);
	}

}
