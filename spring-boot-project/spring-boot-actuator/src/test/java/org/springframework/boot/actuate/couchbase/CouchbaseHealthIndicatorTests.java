package org.springframework.boot.actuate.couchbase;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import com.couchbase.client.core.diagnostics.DiagnosticsResult;
import com.couchbase.client.core.diagnostics.EndpointDiagnostics;
import com.couchbase.client.core.endpoint.EndpointState;
import com.couchbase.client.core.service.ServiceType;
import com.couchbase.client.java.Cluster;
import org.junit.jupiter.api.Test;

import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.Status;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

/**
 * Tests for {@link CouchbaseHealthIndicator}
 *


 */
class CouchbaseHealthIndicatorTests {

	@Test
	@SuppressWarnings("unchecked")
	void couchbaseClusterIsUp() {
		Cluster cluster = mock(Cluster.class);
		CouchbaseHealthIndicator healthIndicator = new CouchbaseHealthIndicator(cluster);
		Map<ServiceType, List<EndpointDiagnostics>> endpoints = Collections.singletonMap(ServiceType.KV,
				Collections.singletonList(new EndpointDiagnostics(ServiceType.KV, EndpointState.CONNECTED, "127.0.0.1",
						"127.0.0.1", Optional.empty(), Optional.of(1234L), Optional.of("endpoint-1"))));

		DiagnosticsResult diagnostics = new DiagnosticsResult(endpoints, "test-sdk", "test-id");
		given(cluster.diagnostics()).willReturn(diagnostics);
		Health health = healthIndicator.health();
		assertThat(health.getStatus()).isEqualTo(Status.UP);
		assertThat(health.getDetails()).containsEntry("sdk", "test-sdk");
		assertThat(health.getDetails()).containsKey("endpoints");
		assertThat((List<Map<String, Object>>) health.getDetails().get("endpoints")).hasSize(1);
		verify(cluster).diagnostics();
	}

	@Test
	@SuppressWarnings("unchecked")
	void couchbaseClusterIsDown() {
		Cluster cluster = mock(Cluster.class);
		CouchbaseHealthIndicator healthIndicator = new CouchbaseHealthIndicator(cluster);
		Map<ServiceType, List<EndpointDiagnostics>> endpoints = Collections.singletonMap(ServiceType.KV,
				Arrays.asList(
						new EndpointDiagnostics(ServiceType.KV, EndpointState.CONNECTED, "127.0.0.1", "127.0.0.1",
								Optional.empty(), Optional.of(1234L), Optional.of("endpoint-1")),
						new EndpointDiagnostics(ServiceType.KV, EndpointState.CONNECTING, "127.0.0.1", "127.0.0.1",
								Optional.empty(), Optional.of(1234L), Optional.of("endpoint-2"))));
		DiagnosticsResult diagnostics = new DiagnosticsResult(endpoints, "test-sdk", "test-id");
		given(cluster.diagnostics()).willReturn(diagnostics);
		Health health = healthIndicator.health();
		assertThat(health.getStatus()).isEqualTo(Status.DOWN);
		assertThat(health.getDetails()).containsEntry("sdk", "test-sdk");
		assertThat(health.getDetails()).containsKey("endpoints");
		assertThat((List<Map<String, Object>>) health.getDetails().get("endpoints")).hasSize(2);
		verify(cluster).diagnostics();
	}

}
