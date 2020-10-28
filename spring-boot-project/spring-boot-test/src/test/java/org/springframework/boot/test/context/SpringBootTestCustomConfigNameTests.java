package org.springframework.boot.test.context;

import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Tests for {@link SpringBootTest @SpringBootTest} with a custom config name
 *

 */
@SpringBootTest(properties = "spring.config.name=custom-config-name")
class SpringBootTestCustomConfigNameTests {

	@Value("${test.foo}")
	private String foo;

	@Test
	void propertyIsLoadedFromConfigFileWithCustomName() {
		assertThat(this.foo).isEqualTo("bar");
	}

	@Configuration(proxyBeanMethods = false)
	static class TestConfiguration {

		PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurer() {
			return new PropertySourcesPlaceholderConfigurer();
		}

	}

}
