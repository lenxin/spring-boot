package org.springframework.boot.actuate.health;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;

import org.springframework.boot.actuate.endpoint.http.ApiVersion;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;

/**
 * Test for {@link CompositeHealth}.
 *

 */
class CompositeHealthTests {

	@Test
	void createWhenStatusIsNullThrowsException() {
		assertThatIllegalArgumentException()
				.isThrownBy(() -> new CompositeHealth(ApiVersion.V3, null, Collections.emptyMap()))
				.withMessage("Status must not be null");
	}

	@Test
	void getStatusReturnsStatus() {
		CompositeHealth health = new CompositeHealth(ApiVersion.V3, Status.UP, Collections.emptyMap());
		assertThat(health.getStatus()).isEqualTo(Status.UP);
	}

	@Test
	void getComponentReturnsComponents() {
		Map<String, HealthComponent> components = new LinkedHashMap<>();
		components.put("a", Health.up().build());
		CompositeHealth health = new CompositeHealth(ApiVersion.V3, Status.UP, components);
		assertThat(health.getComponents()).isEqualTo(components);
	}

	@Test
	void serializeV3WithJacksonReturnsValidJson() throws Exception {
		Map<String, HealthComponent> components = new LinkedHashMap<>();
		components.put("db1", Health.up().build());
		components.put("db2", Health.down().withDetail("a", "b").build());
		CompositeHealth health = new CompositeHealth(ApiVersion.V3, Status.UP, components);
		ObjectMapper mapper = new ObjectMapper();
		String json = mapper.writeValueAsString(health);
		assertThat(json).isEqualTo("{\"status\":\"UP\",\"components\":{\"db1\":{\"status\":\"UP\"},"
				+ "\"db2\":{\"status\":\"DOWN\",\"details\":{\"a\":\"b\"}}}}");
	}

	@Test
	void serializeV2WithJacksonReturnsValidJson() throws Exception {
		Map<String, HealthComponent> components = new LinkedHashMap<>();
		components.put("db1", Health.up().build());
		components.put("db2", Health.down().withDetail("a", "b").build());
		CompositeHealth health = new CompositeHealth(ApiVersion.V2, Status.UP, components);
		ObjectMapper mapper = new ObjectMapper();
		String json = mapper.writeValueAsString(health);
		assertThat(json).isEqualTo("{\"status\":\"UP\",\"details\":{\"db1\":{\"status\":\"UP\"},"
				+ "\"db2\":{\"status\":\"DOWN\",\"details\":{\"a\":\"b\"}}}}");
	}

}
