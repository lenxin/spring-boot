package org.springframework.boot.autoconfigure.http;

import org.junit.jupiter.api.Test;

import org.springframework.boot.autoconfigure.AutoConfigurations;
import org.springframework.boot.test.context.runner.ApplicationContextRunner;
import org.springframework.boot.testsupport.classpath.ClassPathExclusions;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Tests for {@link HttpMessageConvertersAutoConfiguration} without Jackson on the
 * classpath.
 *

 */
@ClassPathExclusions("jackson-*.jar")
class HttpMessageConvertersAutoConfigurationWithoutJacksonTests {

	private final ApplicationContextRunner contextRunner = new ApplicationContextRunner()
			.withConfiguration(AutoConfigurations.of(HttpMessageConvertersAutoConfiguration.class));

	@Test
	void autoConfigurationWorksWithSpringHateoasButWithoutJackson() {
		this.contextRunner.run((context) -> assertThat(context).hasSingleBean(HttpMessageConverters.class));
	}

}
