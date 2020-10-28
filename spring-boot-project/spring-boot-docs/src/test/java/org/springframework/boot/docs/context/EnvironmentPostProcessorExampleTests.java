package org.springframework.boot.docs.context;

import org.junit.jupiter.api.Test;

import org.springframework.boot.SpringApplication;
import org.springframework.core.env.StandardEnvironment;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Tests for {@link EnvironmentPostProcessorExample}.
 *

 */
class EnvironmentPostProcessorExampleTests {

	private final StandardEnvironment environment = new StandardEnvironment();

	@Test
	void applyEnvironmentPostProcessor() {
		assertThat(this.environment.containsProperty("test.foo.bar")).isFalse();
		new EnvironmentPostProcessorExample().postProcessEnvironment(this.environment, new SpringApplication());
		assertThat(this.environment.containsProperty("test.foo.bar")).isTrue();
		assertThat(this.environment.getProperty("test.foo.bar")).isEqualTo("value");
	}

}
