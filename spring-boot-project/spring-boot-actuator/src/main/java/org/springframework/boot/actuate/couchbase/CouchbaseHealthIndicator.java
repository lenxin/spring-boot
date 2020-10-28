package org.springframework.boot.actuate.couchbase;

import com.couchbase.client.core.diagnostics.DiagnosticsResult;
import com.couchbase.client.java.Cluster;

import org.springframework.boot.actuate.health.AbstractHealthIndicator;
import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.util.Assert;

/**
 * {@link HealthIndicator} for Couchbase.
 *


 * @since 2.0.0
 */
public class CouchbaseHealthIndicator extends AbstractHealthIndicator {

	private final Cluster cluster;

	/**
	 * Create an indicator with the specified {@link Cluster}.
	 * @param cluster the Couchbase Cluster
	 * @since 2.0.6
	 */
	public CouchbaseHealthIndicator(Cluster cluster) {
		super("Couchbase health check failed");
		Assert.notNull(cluster, "Cluster must not be null");
		this.cluster = cluster;
	}

	@Override
	protected void doHealthCheck(Health.Builder builder) throws Exception {
		DiagnosticsResult diagnostics = this.cluster.diagnostics();
		new CouchbaseHealth(diagnostics).applyTo(builder);
	}

}
