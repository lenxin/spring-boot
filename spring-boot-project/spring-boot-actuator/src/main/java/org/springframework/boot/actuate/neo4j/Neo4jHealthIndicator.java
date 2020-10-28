package org.springframework.boot.actuate.neo4j;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.neo4j.driver.AccessMode;
import org.neo4j.driver.Driver;
import org.neo4j.driver.Result;
import org.neo4j.driver.Session;
import org.neo4j.driver.SessionConfig;
import org.neo4j.driver.exceptions.SessionExpiredException;
import org.neo4j.driver.summary.ResultSummary;

import org.springframework.boot.actuate.health.AbstractHealthIndicator;
import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;

/**
 * {@link HealthIndicator} that tests the status of a Neo4j by executing a Cypher
 * statement and extracting server and database information.
 *



 * @since 2.0.0
 */
public class Neo4jHealthIndicator extends AbstractHealthIndicator {

	private static final Log logger = LogFactory.getLog(Neo4jHealthIndicator.class);

	/**
	 * The Cypher statement used to verify Neo4j is up.
	 */
	static final String CYPHER = "CALL dbms.components() YIELD name, edition WHERE name = 'Neo4j Kernel' RETURN edition";

	/**
	 * Message logged before retrying a health check.
	 */
	static final String MESSAGE_SESSION_EXPIRED = "Neo4j session has expired, retrying one single time to retrieve server health.";

	/**
	 * The default session config to use while connecting.
	 */
	static final SessionConfig DEFAULT_SESSION_CONFIG = SessionConfig.builder().withDefaultAccessMode(AccessMode.WRITE)
			.build();

	private final Driver driver;

	private final Neo4jHealthDetailsHandler healthDetailsHandler;

	public Neo4jHealthIndicator(Driver driver) {
		super("Neo4j health check failed");
		this.driver = driver;
		this.healthDetailsHandler = new Neo4jHealthDetailsHandler();
	}

	@Override
	protected void doHealthCheck(Health.Builder builder) {
		try {
			try {
				runHealthCheckQuery(builder);
			}
			catch (SessionExpiredException ex) {
				// Retry one time when the session has been expired
				logger.warn(MESSAGE_SESSION_EXPIRED);
				runHealthCheckQuery(builder);
			}
		}
		catch (Exception ex) {
			builder.down().withException(ex);
		}
	}

	private void runHealthCheckQuery(Health.Builder builder) {
		// We use WRITE here to make sure UP is returned for a server that supports
		// all possible workloads
		try (Session session = this.driver.session(DEFAULT_SESSION_CONFIG)) {
			Result result = session.run(CYPHER);
			String edition = result.single().get("edition").asString();
			ResultSummary resultSummary = result.consume();
			this.healthDetailsHandler.addHealthDetails(builder, edition, resultSummary);
		}
	}

}
