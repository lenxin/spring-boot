package smoketest.rsocket;

import io.rsocket.metadata.WellKnownMimeType;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.rsocket.context.LocalRSocketServerPort;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.messaging.rsocket.RSocketRequester;
import org.springframework.security.rsocket.metadata.SimpleAuthenticationEncoder;
import org.springframework.security.rsocket.metadata.UsernamePasswordMetadata;
import org.springframework.util.MimeTypeUtils;

@SpringBootTest(properties = "spring.rsocket.server.port=0")
public class SampleRSocketApplicationTests {

	@LocalRSocketServerPort
	private int port;

	@Autowired
	private RSocketRequester.Builder builder;

	@Test
	void unauthenticatedAccessToRSocketEndpoint() {
		RSocketRequester requester = this.builder.tcp("localhost", this.port);
		Mono<Project> result = requester.route("find.project.spring-boot").retrieveMono(Project.class);
		StepVerifier.create(result).expectErrorMessage("Access Denied").verify();
	}

	@Test
	void rSocketEndpoint() {
		RSocketRequester requester = this.builder
				.rsocketStrategies((builder) -> builder.encoder(new SimpleAuthenticationEncoder()))
				.setupMetadata(new UsernamePasswordMetadata("user", "password"),
						MimeTypeUtils.parseMimeType(WellKnownMimeType.MESSAGE_RSOCKET_AUTHENTICATION.getString()))
				.tcp("localhost", this.port);
		Mono<Project> result = requester.route("find.project.spring-boot").retrieveMono(Project.class);
		StepVerifier.create(result)
				.assertNext((project) -> Assertions.assertThat(project.getName()).isEqualTo("spring-boot"))
				.verifyComplete();
	}

}
