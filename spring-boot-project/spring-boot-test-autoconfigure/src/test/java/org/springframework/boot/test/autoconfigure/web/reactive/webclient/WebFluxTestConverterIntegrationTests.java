package org.springframework.boot.test.autoconfigure.web.reactive.webclient;

import java.util.UUID;

import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.reactive.server.WebTestClient;

/**
 * Tests for {@link WebFluxTest @WebFluxTest} to validate converters are discovered.
 *

 */
@WithMockUser
@WebFluxTest(controllers = ExampleController2.class)
class WebFluxTestConverterIntegrationTests {

	@Autowired
	private WebTestClient webClient;

	@Test
	void shouldFindConverter() {
		UUID id = UUID.randomUUID();
		this.webClient.get().uri("/two/" + id).exchange().expectStatus().isOk().expectBody(String.class)
				.isEqualTo(id + "two");
	}

}
