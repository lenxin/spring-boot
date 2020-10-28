package org.springframework.boot.test.context;

import org.junit.jupiter.api.Test;

import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Tests for {@link SpringBootTest @SpringBootTest} configured with a user-defined
 * {@link RestTemplate} that is named {@code testRestTemplate}.
 *


 */
@DirtiesContext
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT, properties = { "value=123" })
class SpringBootTestUserDefinedTestRestTemplateTests extends AbstractSpringBootTestWebServerWebEnvironmentTests {

	@Test
	void restTemplateIsUserDefined() {
		assertThat(getContext().getBean("testRestTemplate")).isInstanceOf(RestTemplate.class);
	}

	// gh-7711

	@Configuration(proxyBeanMethods = false)
	@EnableWebMvc
	@RestController
	static class Config extends AbstractConfig {

		@Bean
		RestTemplate testRestTemplate() {
			return new RestTemplate();
		}

	}

}
