package org.springframework.boot.docs.test.autoconfigure.restdocs.webclient;

// tag::source[]
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.test.web.reactive.server.WebTestClient;

import static org.springframework.restdocs.webtestclient.WebTestClientRestDocumentation.document;

@WebFluxTest
@AutoConfigureRestDocs
class UsersDocumentationTests {

	@Autowired
	private WebTestClient webTestClient;

	@Test
	void listUsers() {
		this.webTestClient.get().uri("/").exchange().expectStatus().isOk().expectBody()
				.consumeWith(document("list-users"));
	}

}
// end::source[]
