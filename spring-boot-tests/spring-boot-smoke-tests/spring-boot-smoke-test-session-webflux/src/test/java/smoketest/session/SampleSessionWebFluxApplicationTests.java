package smoketest.session;

import java.time.Duration;
import java.util.Base64;

import org.junit.jupiter.api.Test;
import reactor.util.function.Tuples;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.web.reactive.function.client.WebClient;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Integration tests for {@link SampleSessionWebFluxApplication}.
 *

 */
@SpringBootTest(properties = "spring.session.timeout:2", webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class SampleSessionWebFluxApplicationTests {

	@LocalServerPort
	private int port;

	@Autowired
	private WebClient.Builder webClientBuilder;

	@Test
	void userDefinedMappingsSecureByDefault() {
		WebClient client = this.webClientBuilder.baseUrl("http://localhost:" + this.port + "/").build();
		client.get().header("Authorization", getBasicAuth()).exchangeToMono((response) -> {
			assertThat(response.statusCode()).isEqualTo(HttpStatus.OK);
			return response.bodyToMono(String.class)
					.map((sessionId) -> Tuples.of(response.cookies().getFirst("SESSION").getValue(), sessionId));
		}).flatMap((tuple) -> {
			String sesssionCookie = tuple.getT1();
			return client.get().cookie("SESSION", sesssionCookie).exchangeToMono((response) -> {
				assertThat(response.statusCode()).isEqualTo(HttpStatus.OK);
				return response.bodyToMono(String.class)
						.doOnNext((sessionId) -> assertThat(sessionId).isEqualTo(tuple.getT2()))
						.thenReturn(sesssionCookie);
			});
		}).delayElement(Duration.ofSeconds(2))
				.flatMap((sessionCookie) -> client.get().cookie("SESSION", sessionCookie).exchangeToMono((response) -> {
					assertThat(response.statusCode()).isEqualTo(HttpStatus.UNAUTHORIZED);
					return response.releaseBody();
				})).block(Duration.ofSeconds(30));
	}

	private String getBasicAuth() {
		return "Basic " + Base64.getEncoder().encodeToString("user:password".getBytes());
	}

}
