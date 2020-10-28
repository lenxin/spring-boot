package org.springframework.boot.test.context;

import javax.servlet.ServletContext;

import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Tests for {@link SpringBootTest @SpringBootTest} configured with
 * {@link WebEnvironment#MOCK}.
 *


 */
@SpringBootTest
@DirtiesContext
@WebAppConfiguration("src/mymain/mywebapp")
class SpringBootTestWebEnvironmentMockWithWebAppConfigurationTests {

	@Autowired
	private ServletContext servletContext;

	@Test
	void resourcePath() {
		assertThat(this.servletContext).hasFieldOrPropertyWithValue("resourceBasePath", "src/mymain/mywebapp");
	}

	@Configuration(proxyBeanMethods = false)
	@EnableWebMvc
	static class Config {

		@Bean
		static PropertySourcesPlaceholderConfigurer propertyPlaceholder() {
			return new PropertySourcesPlaceholderConfigurer();
		}

	}

}
