package org.springframework.boot.test.autoconfigure.web.reactive.webclient;

import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.reactive.server.WebTestClient;

/**
 * Tests for {@link WebFluxTest @WebFluxTest} to validate the {@link WebTestClient
 * WebTestClient's} codecs are customized.
 *

 */
@WithMockUser
@WebFluxTest(controllers = JsonController.class)
class WebFluxTestWebTestClientCodecCustomizationIntegrationTests {

	@Autowired
	private WebTestClient webClient;

	@Test
	void shouldBeAbleToCreatePojoViaParametersModule() {
		this.webClient.get().uri("/json").exchange().expectStatus().isOk().expectBody(ExamplePojo.class);
	}

}
