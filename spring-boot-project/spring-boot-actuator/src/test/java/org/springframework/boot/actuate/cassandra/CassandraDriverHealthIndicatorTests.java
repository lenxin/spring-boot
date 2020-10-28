package org.springframework.boot.actuate.cassandra;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import com.datastax.oss.driver.api.core.CqlSession;
import com.datastax.oss.driver.api.core.DriverTimeoutException;
import com.datastax.oss.driver.api.core.Version;
import com.datastax.oss.driver.api.core.metadata.Metadata;
import com.datastax.oss.driver.api.core.metadata.Node;
import com.datastax.oss.driver.api.core.metadata.NodeState;
import org.junit.jupiter.api.Test;

import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.Status;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

/**
 * Tests for {@link CassandraDriverHealthIndicator}.
 *


 */
class CassandraDriverHealthIndicatorTests {

	@Test
	void createWhenCqlSessionIsNullShouldThrowException() {
		assertThatIllegalArgumentException().isThrownBy(() -> new CassandraDriverHealthIndicator(null));
	}

	@Test
	void healthWithOneHealthyNodeShouldReturnUp() {
		CqlSession session = mockCqlSessionWithNodeState(NodeState.UP);
		CassandraDriverHealthIndicator healthIndicator = new CassandraDriverHealthIndicator(session);
		Health health = healthIndicator.health();
		assertThat(health.getStatus()).isEqualTo(Status.UP);
	}

	@Test
	void healthWithOneUnhealthyNodeShouldReturnDown() {
		CqlSession session = mockCqlSessionWithNodeState(NodeState.DOWN);
		CassandraDriverHealthIndicator healthIndicator = new CassandraDriverHealthIndicator(session);
		Health health = healthIndicator.health();
		assertThat(health.getStatus()).isEqualTo(Status.DOWN);
	}

	@Test
	void healthWithOneUnknownNodeShouldReturnDown() {
		CqlSession session = mockCqlSessionWithNodeState(NodeState.UNKNOWN);
		CassandraDriverHealthIndicator healthIndicator = new CassandraDriverHealthIndicator(session);
		Health health = healthIndicator.health();
		assertThat(health.getStatus()).isEqualTo(Status.DOWN);
	}

	@Test
	void healthWithOneForcedDownNodeShouldReturnDown() {
		CqlSession session = mockCqlSessionWithNodeState(NodeState.FORCED_DOWN);
		CassandraDriverHealthIndicator healthIndicator = new CassandraDriverHealthIndicator(session);
		Health health = healthIndicator.health();
		assertThat(health.getStatus()).isEqualTo(Status.DOWN);
	}

	@Test
	void healthWithOneHealthyNodeAndOneUnhealthyNodeShouldReturnUp() {
		CqlSession session = mockCqlSessionWithNodeState(NodeState.UP, NodeState.DOWN);
		CassandraDriverHealthIndicator healthIndicator = new CassandraDriverHealthIndicator(session);
		Health health = healthIndicator.health();
		assertThat(health.getStatus()).isEqualTo(Status.UP);
	}

	@Test
	void healthWithOneHealthyNodeAndOneUnknownNodeShouldReturnUp() {
		CqlSession session = mockCqlSessionWithNodeState(NodeState.UP, NodeState.UNKNOWN);
		CassandraDriverHealthIndicator healthIndicator = new CassandraDriverHealthIndicator(session);
		Health health = healthIndicator.health();
		assertThat(health.getStatus()).isEqualTo(Status.UP);
	}

	@Test
	void healthWithOneHealthyNodeAndOneForcedDownNodeShouldReturnUp() {
		CqlSession session = mockCqlSessionWithNodeState(NodeState.UP, NodeState.FORCED_DOWN);
		CassandraDriverHealthIndicator healthIndicator = new CassandraDriverHealthIndicator(session);
		Health health = healthIndicator.health();
		assertThat(health.getStatus()).isEqualTo(Status.UP);
	}

	@Test
	void healthWithNodeVersionShouldAddVersionDetail() {
		CqlSession session = mock(CqlSession.class);
		Metadata metadata = mock(Metadata.class);
		given(session.getMetadata()).willReturn(metadata);
		Node node = mock(Node.class);
		given(node.getState()).willReturn(NodeState.UP);
		given(node.getCassandraVersion()).willReturn(Version.V4_0_0);
		given(metadata.getNodes()).willReturn(createNodesWithRandomUUID(Collections.singletonList(node)));
		CassandraDriverHealthIndicator healthIndicator = new CassandraDriverHealthIndicator(session);
		Health health = healthIndicator.health();
		assertThat(health.getStatus()).isEqualTo(Status.UP);
		assertThat(health.getDetails().get("version")).isEqualTo(Version.V4_0_0);
	}

	@Test
	void healthWithoutNodeVersionShouldNotAddVersionDetail() {
		CqlSession session = mockCqlSessionWithNodeState(NodeState.UP);
		CassandraDriverHealthIndicator healthIndicator = new CassandraDriverHealthIndicator(session);
		Health health = healthIndicator.health();
		assertThat(health.getStatus()).isEqualTo(Status.UP);
		assertThat(health.getDetails().get("version")).isNull();
	}

	@Test
	void healthWithcassandraDownShouldReturnDown() {
		CqlSession session = mock(CqlSession.class);
		given(session.getMetadata()).willThrow(new DriverTimeoutException("Test Exception"));
		CassandraDriverHealthIndicator healthIndicator = new CassandraDriverHealthIndicator(session);
		Health health = healthIndicator.health();
		assertThat(health.getStatus()).isEqualTo(Status.DOWN);
		assertThat(health.getDetails().get("error"))
				.isEqualTo(DriverTimeoutException.class.getName() + ": Test Exception");
	}

	private CqlSession mockCqlSessionWithNodeState(NodeState... nodeStates) {
		CqlSession session = mock(CqlSession.class);
		Metadata metadata = mock(Metadata.class);
		List<Node> nodes = new ArrayList<>();
		for (NodeState nodeState : nodeStates) {
			Node node = mock(Node.class);
			given(node.getState()).willReturn(nodeState);
			nodes.add(node);
		}
		given(session.getMetadata()).willReturn(metadata);
		given(metadata.getNodes()).willReturn(createNodesWithRandomUUID(nodes));
		return session;
	}

	private Map<UUID, Node> createNodesWithRandomUUID(List<Node> nodes) {
		Map<UUID, Node> indexedNodes = new HashMap<>();
		nodes.forEach((node) -> indexedNodes.put(UUID.randomUUID(), node));
		return indexedNodes;
	}

}
