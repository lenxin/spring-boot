package smoketest.actuator;

import java.util.Map;

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
 * Integration tests for separate management and main service ports.
 *

 */
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT, properties = { "management.server.port=0",
		"management.endpoints.web.base-path=/admin", "management.endpoint.health.show-details=never" })
class ManagementPortAndPathSampleActuatorApplicationTests {

	@LocalServerPort
	private int port;

	@LocalManagementPort
	private int managementPort;

	@Autowired
	private Environment environment;

	@Test
	void testHome() {
		ResponseEntity<Map<String, Object>> entity = asMapEntity(
				new TestRestTemplate("user", "password").getForEntity("http://localhost:" + this.port, Map.class));
		assertThat(entity.getStatusCode()).isEqualTo(HttpStatus.OK);
		assertThat(entity.getBody().get("message")).isEqualTo("Hello Phil");
	}

	@Test
	void testMetrics() {
		testHome(); // makes sure some requests have been made
		ResponseEntity<Map<String, Object>> entity = asMapEntity(new TestRestTemplate()
				.getForEntity("http://localhost:" + this.managementPort + "/admin/metrics", Map.class));
		assertThat(entity.getStatusCode()).isEqualTo(HttpStatus.UNAUTHORIZED);
	}

	@Test
	void testHealth() {
		ResponseEntity<String> entity = new TestRestTemplate().withBasicAuth("user", "password")
				.getForEntity("http://localhost:" + this.managementPort + "/admin/health", String.class);
		assertThat(entity.getStatusCode()).isEqualTo(HttpStatus.OK);
		assertThat(entity.getBody()).isEqualTo("{\"status\":\"UP\",\"groups\":[\"live\",\"ready\"]}");
	}

	@Test
	void testEnvNotFound() {
		String unknownProperty = "test-does-not-exist";
		assertThat(this.environment.containsProperty(unknownProperty)).isFalse();
		ResponseEntity<String> entity = new TestRestTemplate().withBasicAuth("user", "password").getForEntity(
				"http://localhost:" + this.managementPort + "/admin/env/" + unknownProperty, String.class);
		assertThat(entity.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
	}

	@Test
	void testMissing() {
		ResponseEntity<String> entity = new TestRestTemplate("user", "password")
				.getForEntity("http://localhost:" + this.managementPort + "/admin/missing", String.class);
		assertThat(entity.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
		assertThat(entity.getBody()).contains("\"status\":404");
	}

	@Test
	void testErrorPage() {
		ResponseEntity<Map<String, Object>> entity = asMapEntity(new TestRestTemplate("user", "password")
				.getForEntity("http://localhost:" + this.port + "/error", Map.class));
		assertThat(entity.getStatusCode()).isEqualTo(HttpStatus.INTERNAL_SERVER_ERROR);
		assertThat(entity.getBody().get("status")).isEqualTo(999);
	}

	@Test
	void testManagementErrorPage() {
		ResponseEntity<Map<String, Object>> entity = asMapEntity(new TestRestTemplate("user", "password")
				.getForEntity("http://localhost:" + this.managementPort + "/error", Map.class));
		assertThat(entity.getStatusCode()).isEqualTo(HttpStatus.OK);
		assertThat(entity.getBody().get("status")).isEqualTo(999);
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	static <K, V> ResponseEntity<Map<K, V>> asMapEntity(ResponseEntity<Map> entity) {
		return (ResponseEntity) entity;
	}

}
