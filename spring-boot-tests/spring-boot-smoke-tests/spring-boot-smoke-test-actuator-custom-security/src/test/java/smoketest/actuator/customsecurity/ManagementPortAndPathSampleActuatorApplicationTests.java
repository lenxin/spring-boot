package smoketest.actuator.customsecurity;

import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.autoconfigure.web.server.LocalManagementPort;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Integration tests for separate management and main service ports with custom management
 * context path.
 *


 */
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT,
		properties = { "management.server.port=0", "management.server.base-path=/management" })
class ManagementPortAndPathSampleActuatorApplicationTests extends AbstractSampleActuatorCustomSecurityTests {

	@LocalServerPort
	private int port;

	@LocalManagementPort
	private int managementPort;

	@Autowired
	private Environment environment;

	@Test
	void testMissing() {
		ResponseEntity<String> entity = new TestRestTemplate("admin", "admin")
				.getForEntity("http://localhost:" + this.managementPort + "/management/actuator/missing", String.class);
		assertThat(entity.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
		assertThat(entity.getBody()).contains("\"status\":404");
	}

	@Override
	String getPath() {
		return "http://localhost:" + this.port;
	}

	@Override
	String getManagementPath() {
		return "http://localhost:" + this.managementPort + "/management";
	}

	@Override
	Environment getEnvironment() {
		return this.environment;
	}

}
