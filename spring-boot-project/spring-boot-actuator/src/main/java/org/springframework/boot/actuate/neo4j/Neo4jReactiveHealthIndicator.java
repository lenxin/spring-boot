package org.springframework.boot.actuate.neo4j;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.neo4j.driver.Driver;
import org.neo4j.driver.exceptions.SessionExpiredException;
import org.neo4j.driver.reactive.RxResult;
import org.neo4j.driver.reactive.RxSession;
import org.neo4j.driver.summary.ResultSummary;
import reactor.core.publisher.Mono;
import reactor.util.function.Tuple2;
import reactor.util.retry.Retry;

import org.springframework.boot.actuate.health.AbstractReactiveHealthIndicator;
import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.ReactiveHealthIndicator;

/**
 * {@link ReactiveHealthIndicator} that tests the status of a Neo4j by executing a Cypher
 * statement and extracting server and database information.
 *


 * @since 2.4.0
 */
public final class Neo4jReactiveHealthIndicator extends AbstractReactiveHealthIndicator {

	private static final Log logger = LogFactory.getLog(Neo4jReactiveHealthIndicator.class);

	private final Driver driver;

	private final Neo4jHealthDetailsHandler healthDetailsHandler;

	public Neo4jReactiveHealthIndicator(Driver driver) {
		this.driver = driver;
		this.healthDetailsHandler = new Neo4jHealthDetailsHandler();
	}

	@Override
	protected Mono<Health> doHealthCheck(Health.Builder builder) {
		return runHealthCheckQuery()
				.doOnError(SessionExpiredException.class,
						(e) -> logger.warn(Neo4jHealthIndicator.MESSAGE_SESSION_EXPIRED))
				.retryWhen(Retry.max(1).filter(SessionExpiredException.class::isInstance)).map((result) -> {
					this.healthDetailsHandler.addHealthDetails(builder, result.getT1(), result.getT2());
					return builder.build();
				});
	}

	Mono<Tuple2<String, ResultSummary>> runHealthCheckQuery() {
		// We use WRITE here to make sure UP is returned for a server that supports
		// all possible workloads
		return Mono.using(() -> this.driver.rxSession(Neo4jHealthIndicator.DEFAULT_SESSION_CONFIG), (session) -> {
			RxResult result = session.run(Neo4jHealthIndicator.CYPHER);
			return Mono.from(result.records()).map((record) -> record.get("edition").asString())
					.zipWhen((edition) -> Mono.from(result.consume()));
		}, RxSession::close);
	}

}
