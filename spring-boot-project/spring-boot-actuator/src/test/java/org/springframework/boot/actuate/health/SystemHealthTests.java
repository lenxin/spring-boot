package org.springframework.boot.actuate.health;

import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;

import org.springframework.boot.actuate.endpoint.http.ApiVersion;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Tests for {@link SystemHealth}.
 *

 */
class SystemHealthTests {

	@Test
	void serializeWithJacksonReturnsValidJson() throws Exception {
		Map<String, HealthComponent> components = new LinkedHashMap<>();
		components.put("db1", Health.up().build());
		components.put("db2", Health.down().withDetail("a", "b").build());
		Set<String> groups = new LinkedHashSet<>(Arrays.asList("liveness", "readiness"));
		CompositeHealth health = new SystemHealth(ApiVersion.V3, Status.UP, components, groups);
		ObjectMapper mapper = new ObjectMapper();
		String json = mapper.writeValueAsString(health);
		assertThat(json).isEqualTo("{\"status\":\"UP\",\"components\":{\"db1\":{\"status\":\"UP\"},"
				+ "\"db2\":{\"status\":\"DOWN\",\"details\":{\"a\":\"b\"}}},"
				+ "\"groups\":[\"liveness\",\"readiness\"]}");
	}

}
