package org.springframework.boot.actuate.metrics;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.MockClock;
import io.micrometer.core.instrument.binder.jvm.JvmMemoryMetrics;
import io.micrometer.core.instrument.simple.SimpleConfig;
import io.micrometer.core.instrument.simple.SimpleMeterRegistry;

import org.springframework.boot.actuate.endpoint.web.test.WebEndpointTest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.web.reactive.server.WebTestClient;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Web integration tests for {@link MetricsEndpoint}.
 *


 */
class MetricsEndpointWebIntegrationTests {

	private static MeterRegistry registry = new SimpleMeterRegistry(SimpleConfig.DEFAULT, new MockClock());

	private final ObjectMapper mapper = new ObjectMapper();

	@WebEndpointTest
	@SuppressWarnings("unchecked")
	void listNames(WebTestClient client) throws IOException {
		String responseBody = client.get().uri("/actuator/metrics").exchange().expectStatus().isOk()
				.expectBody(String.class).returnResult().getResponseBody();
		Map<String, List<String>> names = this.mapper.readValue(responseBody, Map.class);
		assertThat(names.get("names")).containsOnlyOnce("jvm.memory.used");
	}

	@WebEndpointTest
	void selectByName(WebTestClient client) {
		client.get().uri("/actuator/metrics/jvm.memory.used").exchange().expectStatus().isOk().expectBody()
				.jsonPath("$.name").isEqualTo("jvm.memory.used");
	}

	@WebEndpointTest
	void selectByTag(WebTestClient client) {
		client.get().uri("/actuator/metrics/jvm.memory.used?tag=id:Compressed%20Class%20Space").exchange()
				.expectStatus().isOk().expectBody().jsonPath("$.name").isEqualTo("jvm.memory.used");
	}

	@Configuration(proxyBeanMethods = false)
	static class TestConfiguration {

		@Bean
		MeterRegistry registry() {
			return registry;
		}

		@Bean
		MetricsEndpoint metricsEndpoint(MeterRegistry meterRegistry) {
			return new MetricsEndpoint(meterRegistry);
		}

		@Bean
		JvmMemoryMetrics jvmMemoryMetrics(MeterRegistry meterRegistry) {
			JvmMemoryMetrics memoryMetrics = new JvmMemoryMetrics();
			memoryMetrics.bindTo(meterRegistry);
			return memoryMetrics;
		}

	}

}
