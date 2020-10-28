package org.springframework.boot.test.context;

import org.junit.jupiter.api.Test;

import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Tests for {@link SpringBootTest @SpringBootTest} configured with
 * {@link WebEnvironment#RANDOM_PORT}.
 *


 */
@DirtiesContext
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT, properties = { "value=123" })
class SpringBootTestWebEnvironmentRandomPortTests extends AbstractSpringBootTestWebServerWebEnvironmentTests {

	@Test
	void testRestTemplateShouldUseBuilder() {
		assertThat(getRestTemplate().getRestTemplate().getMessageConverters())
				.hasAtLeastOneElementOfType(MyConverter.class);
	}

	@Configuration(proxyBeanMethods = false)
	@EnableWebMvc
	@RestController
	static class Config extends AbstractConfig {

		@Bean
		RestTemplateBuilder restTemplateBuilder() {
			return new RestTemplateBuilder().additionalMessageConverters(new MyConverter());
		}

	}

	static class MyConverter extends StringHttpMessageConverter {

	}

}
