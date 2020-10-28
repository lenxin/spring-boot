package smoketest.actuator;

import java.util.Map;

import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Integration tests for endpoints configuration.
 *

 */
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT,
		properties = { "management.endpoints.web.base-path=/admin" })
class ManagementPathSampleActuatorApplicationTests {

	@Autowired
	private TestRestTemplate restTemplate;

	@Test
	void testHealth() {
		ResponseEntity<String> entity = this.restTemplate.withBasicAuth("user", "password")
				.getForEntity("/admin/health", String.class);
		assertThat(entity.getStatusCode()).isEqualTo(HttpStatus.OK);
		assertThat(entity.getBody()).contains("\"status\":\"UP\"");
	}

	@Test
	void testHomeIsSecure() {
		ResponseEntity<Map<String, Object>> entity = asMapEntity(this.restTemplate.getForEntity("/", Map.class));
		assertThat(entity.getStatusCode()).isEqualTo(HttpStatus.UNAUTHORIZED);
		assertThat(entity.getBody().get("error")).isEqualTo("Unauthorized");
		assertThat(entity.getHeaders()).doesNotContainKey("Set-Cookie");
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	static <K, V> ResponseEntity<Map<K, V>> asMapEntity(ResponseEntity<Map> entity) {
		return (ResponseEntity) entity;
	}

}
