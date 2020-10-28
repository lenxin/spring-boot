package org.springframework.boot.autoconfigure.web.servlet;

import java.net.URI;

import org.junit.jupiter.api.Test;

import org.springframework.boot.autoconfigure.context.PropertyPlaceholderAutoConfiguration;
import org.springframework.boot.autoconfigure.http.HttpMessageConvertersAutoConfiguration;
import org.springframework.boot.autoconfigure.thymeleaf.ThymeleafAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Integration tests for the welcome page.
 *

 */
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
		properties = { "spring.resources.chain.strategy.content.enabled=true",
				"spring.thymeleaf.prefix=classpath:/templates/thymeleaf/" })
class WelcomePageIntegrationTests {

	@LocalServerPort
	private int port;

	private TestRestTemplate template = new TestRestTemplate();

	@Test
	void contentStrategyWithWelcomePage() throws Exception {
		RequestEntity<?> entity = RequestEntity.get(new URI("http://localhost:" + this.port + "/"))
				.header("Accept", MediaType.ALL.toString()).build();
		ResponseEntity<String> content = this.template.exchange(entity, String.class);
		assertThat(content.getBody()).contains("/custom-");
	}

	@Configuration
	@Import({ PropertyPlaceholderAutoConfiguration.class, WebMvcAutoConfiguration.class,
			HttpMessageConvertersAutoConfiguration.class, ServletWebServerFactoryAutoConfiguration.class,
			DispatcherServletAutoConfiguration.class, ThymeleafAutoConfiguration.class })
	static class TestConfiguration {

		static void main(String[] args) {
			new SpringApplicationBuilder(TestConfiguration.class).run(args);
		}

	}

}
