package smoketest.webflux;

import org.junit.jupiter.api.Test;
import reactor.core.publisher.Mono;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;

/**
 * Basic integration tests for WebFlux application.
 *

 */
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
class SampleWebFluxApplicationTests {

	@Autowired
	private WebTestClient webClient;

	@Test
	void testWelcome() {
		this.webClient.get().uri("/").accept(MediaType.TEXT_PLAIN).exchange().expectBody(String.class)
				.isEqualTo("Hello World");
	}

	@Test
	void testEcho() {
		this.webClient.post().uri("/echo").contentType(MediaType.TEXT_PLAIN).accept(MediaType.TEXT_PLAIN)
				.body(Mono.just("Hello WebFlux!"), String.class).exchange().expectBody(String.class)
				.isEqualTo("Hello WebFlux!");
	}

	@Test
	void testActuatorStatus() {
		this.webClient.get().uri("/actuator/health").accept(MediaType.APPLICATION_JSON).exchange().expectStatus().isOk()
				.expectBody().json("{\"status\":\"UP\"}");
	}

}
