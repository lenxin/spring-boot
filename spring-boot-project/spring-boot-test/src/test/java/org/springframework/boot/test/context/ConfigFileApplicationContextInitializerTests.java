package org.springframework.boot.test.context;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Tests for {@link ConfigFileApplicationContextInitializer}.
 *

 */
@Deprecated
@SuppressWarnings("deprecation")
@ExtendWith(SpringExtension.class)
@DirtiesContext
@ContextConfiguration(classes = ConfigFileApplicationContextInitializerTests.Config.class,
		initializers = ConfigFileApplicationContextInitializer.class)
class ConfigFileApplicationContextInitializerTests {

	@Autowired
	private Environment environment;

	@Test
	void initializerPopulatesEnvironment() {
		assertThat(this.environment.getProperty("foo")).isEqualTo("bucket");
	}

	@Configuration(proxyBeanMethods = false)
	static class Config {

	}

}
