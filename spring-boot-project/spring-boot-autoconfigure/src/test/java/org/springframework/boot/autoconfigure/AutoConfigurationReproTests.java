package org.springframework.boot.autoconfigure;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.web.servlet.ServletWebServerFactoryAutoConfiguration;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Tests to reproduce reported issues.
 *

 */
class AutoConfigurationReproTests {

	private ConfigurableApplicationContext context;

	@AfterEach
	void cleanup() {
		if (this.context != null) {
			this.context.close();
		}
	}

	@Test
	void doesNotEarlyInitializeFactoryBeans() {
		SpringApplication application = new SpringApplication(EarlyInitConfig.class,
				PropertySourcesPlaceholderConfigurer.class, ServletWebServerFactoryAutoConfiguration.class);
		this.context = application.run("--server.port=0");
		String bean = (String) this.context.getBean("earlyInit");
		assertThat(bean).isEqualTo("bucket");
	}

	@Configuration(proxyBeanMethods = false)
	static class Config {

	}

	@Configuration(proxyBeanMethods = false)
	@ImportResource("classpath:/early-init-test.xml")
	static class EarlyInitConfig {

	}

}
