package org.springframework.boot.actuate.cassandra;

import com.datastax.oss.driver.api.core.ConsistencyLevel;
import com.datastax.oss.driver.api.core.cql.SimpleStatement;

import org.springframework.boot.actuate.health.AbstractHealthIndicator;
import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.data.cassandra.core.CassandraOperations;
import org.springframework.util.Assert;

/**
 * Simple implementation of a {@link HealthIndicator} returning status information for
 * Cassandra data stores.
 *


 * @since 2.0.0
 * @deprecated since 2.4.0 in favor of {@link CassandraDriverHealthIndicator}
 */
@Deprecated
public class CassandraHealthIndicator extends AbstractHealthIndicator {

	private static final SimpleStatement SELECT = SimpleStatement
			.newInstance("SELECT release_version FROM system.local").setConsistencyLevel(ConsistencyLevel.LOCAL_ONE);

	private CassandraOperations cassandraOperations;

	public CassandraHealthIndicator() {
		super("Cassandra health check failed");
	}

	/**
	 * Create a new {@link CassandraHealthIndicator} instance.
	 * @param cassandraOperations the Cassandra operations
	 */
	public CassandraHealthIndicator(CassandraOperations cassandraOperations) {
		super("Cassandra health check failed");
		Assert.notNull(cassandraOperations, "CassandraOperations must not be null");
		this.cassandraOperations = cassandraOperations;
	}

	@Override
	protected void doHealthCheck(Health.Builder builder) throws Exception {
		String version = this.cassandraOperations.getCqlOperations().queryForObject(SELECT, String.class);
		builder.up().withDetail("version", version);
	}

}
