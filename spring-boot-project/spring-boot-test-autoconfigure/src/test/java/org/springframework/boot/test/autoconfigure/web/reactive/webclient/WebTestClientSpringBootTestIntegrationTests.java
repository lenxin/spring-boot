package org.springframework.boot.test.autoconfigure.web.reactive.webclient;

import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.test.web.reactive.server.WebTestClient;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Tests for {@link SpringBootTest @SpringBootTest} with
 * {@link AutoConfigureWebTestClient @AutoConfigureWebTestClient} (i.e. full integration
 * test).
 *

 */
@SpringBootTest(properties = "spring.main.web-application-type=reactive", classes = {
		WebTestClientSpringBootTestIntegrationTests.TestConfiguration.class, ExampleWebFluxApplication.class })
@AutoConfigureWebTestClient
class WebTestClientSpringBootTestIntegrationTests {

	@Autowired
	private WebTestClient webClient;

	@Autowired
	private ApplicationContext applicationContext;

	@Test
	void shouldFindController1() {
		this.webClient.get().uri("/one").exchange().expectStatus().isOk().expectBody(String.class).isEqualTo("one");
	}

	@Test
	void shouldFindController2() {
		this.webClient.get().uri("/two").exchange().expectStatus().isOk().expectBody(String.class).isEqualTo("two");
	}

	@Test
	void shouldHaveRealService() {
		assertThat(this.applicationContext.getBeansOfType(ExampleRealService.class)).hasSize(1);
	}

	@Configuration(proxyBeanMethods = false)
	static class TestConfiguration {

		@Bean
		SecurityWebFilterChain springSecurityFilterChain(ServerHttpSecurity http) throws Exception {
			http.authorizeExchange((exchanges) -> exchanges.anyExchange().permitAll());
			return http.build();
		}

	}

}
