package org.springframework.boot.actuate.cassandra;

import java.util.Collection;
import java.util.Optional;

import com.datastax.oss.driver.api.core.CqlSession;
import com.datastax.oss.driver.api.core.metadata.Node;
import com.datastax.oss.driver.api.core.metadata.NodeState;

import org.springframework.boot.actuate.health.AbstractHealthIndicator;
import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.boot.actuate.health.Status;
import org.springframework.util.Assert;

/**
 * Simple implementation of a {@link HealthIndicator} returning status information for
 * Cassandra data stores.
 *


 * @since 2.4.0
 */
public class CassandraDriverHealthIndicator extends AbstractHealthIndicator {

	private final CqlSession session;

	/**
	 * Create a new {@link CassandraDriverHealthIndicator} instance.
	 * @param session the {@link CqlSession}.
	 */
	public CassandraDriverHealthIndicator(CqlSession session) {
		super("Cassandra health check failed");
		Assert.notNull(session, "session must not be null");
		this.session = session;
	}

	@Override
	protected void doHealthCheck(Health.Builder builder) throws Exception {
		Collection<Node> nodes = this.session.getMetadata().getNodes().values();
		Optional<Node> nodeUp = nodes.stream().filter((node) -> node.getState() == NodeState.UP).findAny();
		builder.status(nodeUp.isPresent() ? Status.UP : Status.DOWN);
		nodeUp.map(Node::getCassandraVersion).ifPresent((version) -> builder.withDetail("version", version));
	}

}
