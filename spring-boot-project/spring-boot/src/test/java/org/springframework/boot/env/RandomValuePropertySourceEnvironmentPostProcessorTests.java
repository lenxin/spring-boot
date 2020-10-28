package org.springframework.boot.env;

import org.apache.commons.logging.LogFactory;
import org.junit.jupiter.api.Test;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.context.config.ConfigDataEnvironmentPostProcessor;
import org.springframework.mock.env.MockEnvironment;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;

/**
 * Tests for {@link RandomValuePropertySourceEnvironmentPostProcessor}.
 *

 */
class RandomValuePropertySourceEnvironmentPostProcessorTests {

	private RandomValuePropertySourceEnvironmentPostProcessor postProcessor = new RandomValuePropertySourceEnvironmentPostProcessor(
			LogFactory.getLog(getClass()));

	@Test
	void getOrderIsBeforeConfigData() {
		assertThat(this.postProcessor.getOrder()).isLessThan(ConfigDataEnvironmentPostProcessor.ORDER);
	}

	@Test
	void postProcessEnvironmentAddsPropertySource() {
		MockEnvironment environment = new MockEnvironment();
		this.postProcessor.postProcessEnvironment(environment, mock(SpringApplication.class));
		assertThat(environment.getProperty("random.string")).isNotNull();
	}

}
