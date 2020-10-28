package smoketest.secure.jersey;

import org.junit.jupiter.api.Test;

import org.springframework.boot.actuate.autoconfigure.web.server.LocalManagementPort;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Integration tests for separate management and main service ports with custom
 * application path.
 *

 */
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
		properties = { "management.server.port=0", "spring.jersey.application-path=/example" })
class ManagementPortCustomApplicationPathJerseyTests extends AbstractJerseySecureTests {

	@LocalServerPort
	private int port;

	@LocalManagementPort
	private int managementPort;

	@Test
	void actuatorPathOnMainPortShouldNotMatch() {
		ResponseEntity<String> entity = new TestRestTemplate()
				.getForEntity("http://localhost:" + this.port + "/example/actuator/health", String.class);
		assertThat(entity.getStatusCode()).isEqualTo(HttpStatus.UNAUTHORIZED);
	}

	@Override
	String getPath() {
		return "http://localhost:" + this.port + "/example";
	}

	@Override
	String getManagementPath() {
		return "http://localhost:" + this.managementPort;
	}

}
