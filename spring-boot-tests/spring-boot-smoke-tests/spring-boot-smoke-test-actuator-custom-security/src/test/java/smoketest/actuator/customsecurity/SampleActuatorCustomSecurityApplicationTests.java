package smoketest.actuator.customsecurity;

import java.util.Map;

import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Integration tests for actuator endpoints with custom security configuration.
 *



 */
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
		properties = { "server.error.include-message=always" })
class SampleActuatorCustomSecurityApplicationTests extends AbstractSampleActuatorCustomSecurityTests {

	@LocalServerPort
	private int port;

	@Autowired
	private Environment environment;

	@Override
	String getPath() {
		return "http://localhost:" + this.port;
	}

	@Override
	String getManagementPath() {
		return "http://localhost:" + this.port;
	}

	@Override
	Environment getEnvironment() {
		return this.environment;
	}

	@Test
	void testInsecureApplicationPath() {
		@SuppressWarnings("rawtypes")
		ResponseEntity<Map> entity = restTemplate().getForEntity(getPath() + "/foo", Map.class);
		assertThat(entity.getStatusCode()).isEqualTo(HttpStatus.INTERNAL_SERVER_ERROR);
		@SuppressWarnings("unchecked")
		Map<String, Object> body = entity.getBody();
		assertThat((String) body.get("message")).contains("Expected exception in controller");
	}

	@Test
	void mvcMatchersCanBeUsedToSecureActuators() {
		ResponseEntity<Object> entity = beansRestTemplate().getForEntity(getManagementPath() + "/actuator/beans",
				Object.class);
		assertThat(entity.getStatusCode()).isEqualTo(HttpStatus.OK);
		entity = beansRestTemplate().getForEntity(getManagementPath() + "/actuator/beans/", Object.class);
		assertThat(entity.getStatusCode()).isEqualTo(HttpStatus.OK);
	}

}
