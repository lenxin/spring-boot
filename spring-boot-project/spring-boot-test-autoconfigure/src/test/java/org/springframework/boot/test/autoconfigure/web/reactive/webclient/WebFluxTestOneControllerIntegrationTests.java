package org.springframework.boot.test.autoconfigure.web.reactive.webclient;

import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.reactive.server.WebTestClient;

/**
 * Tests for {@link WebFluxTest @WebFluxTest} when a specific controller is defined.
 *

 */
@WithMockUser
@WebFluxTest(controllers = ExampleController1.class)
class WebFluxTestOneControllerIntegrationTests {

	@Autowired
	private WebTestClient webClient;

	@Test
	void shouldFindController() {
		this.webClient.get().uri("/one").exchange().expectStatus().isOk().expectBody(String.class).isEqualTo("one");
	}

	@Test
	void shouldNotScanOtherController() {
		this.webClient.get().uri("/two").exchange().expectStatus().isNotFound();
	}

}
