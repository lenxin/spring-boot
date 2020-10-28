package org.springframework.boot.test.context;

import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

/**
 * Tests for {@link SpringBootTest @SpringBootTest} configured with
 * {@link ContextConfiguration @ContextConfiguration}.
 *

 */
@DirtiesContext
@SpringBootTest
@ContextConfiguration(classes = SpringBootTestWithContextConfigurationIntegrationTests.Config.class)
class SpringBootTestWithContextConfigurationIntegrationTests {

	@Autowired
	private ApplicationContext context;

	@Test
	void injectsOnlyConfig() {
		assertThat(this.context.getBean(Config.class)).isNotNull();
		assertThatExceptionOfType(NoSuchBeanDefinitionException.class)
				.isThrownBy(() -> this.context.getBean(AdditionalConfig.class));
	}

	@Configuration(proxyBeanMethods = false)
	static class Config {

	}

	@Configuration(proxyBeanMethods = false)
	static class AdditionalConfig {

	}

}
