package smoketest.actuator.customsecurity;

import java.net.URI;
import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.LocalHostUriTemplateHandler;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.ApplicationContext;
import org.springframework.http.HttpStatus;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Integration test for cors preflight requests to management endpoints.
 *

 */
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("cors")
class CorsSampleActuatorApplicationTests {

	private TestRestTemplate testRestTemplate;

	@Autowired
	private ApplicationContext applicationContext;

	@BeforeEach
	void setUp() {
		RestTemplateBuilder builder = new RestTemplateBuilder();
		LocalHostUriTemplateHandler handler = new LocalHostUriTemplateHandler(this.applicationContext.getEnvironment(),
				"http");
		builder = builder.uriTemplateHandler(handler);
		this.testRestTemplate = new TestRestTemplate(builder);
	}

	@Test
	void endpointShouldReturnUnauthorized() {
		ResponseEntity<?> entity = this.testRestTemplate.getForEntity("/actuator/env", Map.class);
		assertThat(entity.getStatusCode()).isEqualTo(HttpStatus.UNAUTHORIZED);
	}

	@Test
	void preflightRequestToEndpointShouldReturnOk() throws Exception {
		RequestEntity<?> healthRequest = RequestEntity.options(new URI("/actuator/env"))
				.header("Origin", "http://localhost:8080").header("Access-Control-Request-Method", "GET").build();
		ResponseEntity<?> exchange = this.testRestTemplate.exchange(healthRequest, Map.class);
		assertThat(exchange.getStatusCode()).isEqualTo(HttpStatus.OK);
	}

	@Test
	void preflightRequestWhenCorsConfigInvalidShouldReturnForbidden() throws Exception {
		RequestEntity<?> entity = RequestEntity.options(new URI("/actuator/env"))
				.header("Origin", "http://localhost:9095").header("Access-Control-Request-Method", "GET").build();
		ResponseEntity<byte[]> exchange = this.testRestTemplate.exchange(entity, byte[].class);
		assertThat(exchange.getStatusCode()).isEqualTo(HttpStatus.FORBIDDEN);
	}

}
