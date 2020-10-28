package smoketest.actuator;

import org.junit.jupiter.api.Test;

import org.springframework.boot.actuate.autoconfigure.web.server.LocalManagementPort;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Integration tests for separate management and main service ports when
 * lazy-initialization is enabled.
 *

 */
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
		properties = { "management.server.port=0", "spring.main.lazy-initialization=true" })
class ManagementPortWithLazyInitializationTests {

	@LocalManagementPort
	private int managementPort;

	@Test
	void testHealth() {
		ResponseEntity<String> entity = new TestRestTemplate().withBasicAuth("user", "password")
				.getForEntity("http://localhost:" + this.managementPort + "/actuator/health", String.class);
		assertThat(entity.getStatusCode()).isEqualTo(HttpStatus.OK);
		assertThat(entity.getBody()).contains("\"status\":\"UP\"");
	}

}
