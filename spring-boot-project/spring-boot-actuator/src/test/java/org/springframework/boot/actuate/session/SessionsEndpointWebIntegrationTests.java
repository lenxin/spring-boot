package org.springframework.boot.actuate.session;

import java.util.Collections;

import net.minidev.json.JSONArray;

import org.springframework.boot.actuate.endpoint.web.test.WebEndpointTest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.session.FindByIndexNameSessionRepository;
import org.springframework.session.MapSession;
import org.springframework.session.Session;
import org.springframework.test.web.reactive.server.WebTestClient;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

/**
 * Integration tests for {@link SessionsEndpoint} exposed by Jersey, Spring MVC, and
 * WebFlux.
 *

 */
class SessionsEndpointWebIntegrationTests {

	private static final Session session = new MapSession();

	@SuppressWarnings("unchecked")
	private static final FindByIndexNameSessionRepository<Session> repository = mock(
			FindByIndexNameSessionRepository.class);

	@WebEndpointTest
	void sessionsForUsernameWithoutUsernameParam(WebTestClient client) {
		client.get().uri((builder) -> builder.path("/actuator/sessions").build()).exchange().expectStatus()
				.isBadRequest();
	}

	@WebEndpointTest
	void sessionsForUsernameNoResults(WebTestClient client) {
		given(repository.findByPrincipalName("user")).willReturn(Collections.emptyMap());
		client.get().uri((builder) -> builder.path("/actuator/sessions").queryParam("username", "user").build())
				.exchange().expectStatus().isOk().expectBody().jsonPath("sessions").isEmpty();
	}

	@WebEndpointTest
	void sessionsForUsernameFound(WebTestClient client) {
		given(repository.findByPrincipalName("user")).willReturn(Collections.singletonMap(session.getId(), session));
		client.get().uri((builder) -> builder.path("/actuator/sessions").queryParam("username", "user").build())
				.exchange().expectStatus().isOk().expectBody().jsonPath("sessions.[*].id")
				.isEqualTo(new JSONArray().appendElement(session.getId()));
	}

	@WebEndpointTest
	void sessionForIdNotFound(WebTestClient client) {
		client.get().uri((builder) -> builder.path("/actuator/sessions/session-id-not-found").build()).exchange()
				.expectStatus().isNotFound();
	}

	@Configuration(proxyBeanMethods = false)
	static class TestConfiguration {

		@Bean
		SessionsEndpoint sessionsEndpoint() {
			return new SessionsEndpoint(repository);
		}

	}

}
