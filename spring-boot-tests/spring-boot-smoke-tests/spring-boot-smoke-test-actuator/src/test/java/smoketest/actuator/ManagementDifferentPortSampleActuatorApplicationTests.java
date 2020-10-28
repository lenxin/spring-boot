package smoketest.actuator;

import org.junit.jupiter.api.Test;

import org.springframework.boot.actuate.autoconfigure.web.server.LocalManagementPort;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Integration tests for separate management and main service ports with empty endpoint
 * base path.
 *

 */
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
		properties = { "management.endpoints.web.base-path=/", "management.server.port=0" })
class ManagementDifferentPortSampleActuatorApplicationTests {

	@LocalManagementPort
	private int managementPort;

	@Test
	void linksEndpointShouldBeAvailable() {
		ResponseEntity<String> entity = new TestRestTemplate("user", "password")
				.getForEntity("http://localhost:" + this.managementPort + "/", String.class);
		assertThat(entity.getStatusCode()).isEqualTo(HttpStatus.OK);
		assertThat(entity.getBody()).contains("\"_links\"");
	}

}
