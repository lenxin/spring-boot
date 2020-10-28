package org.springframework.boot.test.web.reactive.server;

import org.junit.jupiter.api.Test;
import reactor.core.publisher.Mono;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.web.embedded.tomcat.TomcatReactiveWebServerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.core.io.buffer.DefaultDataBufferFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.HttpHandler;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.test.web.reactive.server.WebTestClient.Builder;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

/**
 * Integration test for {@link WebTestClientContextCustomizer}.
 *

 */
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT, properties = "spring.main.web-application-type=reactive")
@DirtiesContext
class WebTestClientContextCustomizerIntegrationTests {

	@Autowired
	private WebTestClient webTestClient;

	@Autowired
	private WebTestClientBuilderCustomizer clientBuilderCustomizer;

	@Test
	void test() {
		verify(this.clientBuilderCustomizer).customize(any(Builder.class));
		this.webTestClient.get().uri("/").exchange().expectBody(String.class).isEqualTo("hello");
	}

	@Configuration(proxyBeanMethods = false)
	@Import({ TestHandler.class, NoWebTestClientBeanChecker.class })
	static class TestConfig {

		@Bean
		TomcatReactiveWebServerFactory webServerFactory() {
			return new TomcatReactiveWebServerFactory(0);
		}

		@Bean
		WebTestClientBuilderCustomizer clientBuilderCustomizer() {
			return mock(WebTestClientBuilderCustomizer.class);
		}

	}

	static class TestHandler implements HttpHandler {

		private static final DefaultDataBufferFactory factory = new DefaultDataBufferFactory();

		@Override
		public Mono<Void> handle(ServerHttpRequest request, ServerHttpResponse response) {
			response.setStatusCode(HttpStatus.OK);
			return response.writeWith(Mono.just(factory.wrap("hello".getBytes())));
		}

	}

}
