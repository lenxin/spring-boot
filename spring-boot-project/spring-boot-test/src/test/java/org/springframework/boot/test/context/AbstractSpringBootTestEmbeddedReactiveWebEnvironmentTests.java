package org.springframework.boot.test.context;

import org.junit.jupiter.api.Test;
import reactor.core.publisher.Mono;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.embedded.tomcat.TomcatReactiveWebServerFactory;
import org.springframework.boot.web.reactive.context.ReactiveWebApplicationContext;
import org.springframework.boot.web.reactive.server.ReactiveWebServerFactory;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.http.server.reactive.HttpHandler;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.server.adapter.WebHttpHandlerBuilder;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Base class for {@link SpringBootTest @SpringBootTest} tests configured to start an
 * embedded reactive container.
 *

 */
abstract class AbstractSpringBootTestEmbeddedReactiveWebEnvironmentTests {

	@LocalServerPort
	private int port = 0;

	@Value("${value}")
	private int value = 0;

	@Autowired
	private ReactiveWebApplicationContext context;

	@Autowired
	private WebTestClient webClient;

	@Autowired
	private TestRestTemplate restTemplate;

	ReactiveWebApplicationContext getContext() {
		return this.context;
	}

	@Test
	void runAndTestHttpEndpoint() {
		assertThat(this.port).isNotEqualTo(8080).isNotEqualTo(0);
		WebTestClient.bindToServer().baseUrl("http://localhost:" + this.port).build().get().uri("/").exchange()
				.expectBody(String.class).isEqualTo("Hello World");
	}

	@Test
	void injectWebTestClient() {
		this.webClient.get().uri("/").exchange().expectBody(String.class).isEqualTo("Hello World");
	}

	@Test
	void injectTestRestTemplate() {
		String body = this.restTemplate.getForObject("/", String.class);
		assertThat(body).isEqualTo("Hello World");
	}

	@Test
	void annotationAttributesOverridePropertiesFile() {
		assertThat(this.value).isEqualTo(123);
	}

	@Configuration(proxyBeanMethods = false)
	static class AbstractConfig {

		@Value("${server.port:8080}")
		private int port = 8080;

		@Bean
		HttpHandler httpHandler(ApplicationContext applicationContext) {
			return WebHttpHandlerBuilder.applicationContext(applicationContext).build();
		}

		@Bean
		ReactiveWebServerFactory webServerFactory() {
			TomcatReactiveWebServerFactory factory = new TomcatReactiveWebServerFactory();
			factory.setPort(this.port);
			return factory;
		}

		@Bean
		static PropertySourcesPlaceholderConfigurer propertyPlaceholder() {
			return new PropertySourcesPlaceholderConfigurer();
		}

		@RequestMapping("/")
		Mono<String> home() {
			return Mono.just("Hello World");
		}

	}

}
