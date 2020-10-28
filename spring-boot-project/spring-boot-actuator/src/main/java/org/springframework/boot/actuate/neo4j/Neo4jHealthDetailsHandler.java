package org.springframework.boot.actuate.neo4j;

import org.neo4j.driver.summary.DatabaseInfo;
import org.neo4j.driver.summary.ResultSummary;
import org.neo4j.driver.summary.ServerInfo;

import org.springframework.boot.actuate.health.Health.Builder;
import org.springframework.util.StringUtils;

/**
 * Handle health check details for a Neo4j server.
 *

 */
class Neo4jHealthDetailsHandler {

	/**
	 * Add health details for the specified {@link ResultSummary} and {@code edition}.
	 * @param builder the {@link Builder} to use
	 * @param edition the edition of the server
	 * @param resultSummary server information
	 */
	void addHealthDetails(Builder builder, String edition, ResultSummary resultSummary) {
		ServerInfo serverInfo = resultSummary.server();
		builder.up().withDetail("server", serverInfo.version() + "@" + serverInfo.address()).withDetail("edition",
				edition);
		DatabaseInfo databaseInfo = resultSummary.database();
		if (StringUtils.hasText(databaseInfo.name())) {
			builder.withDetail("database", databaseInfo.name());
		}
	}

}
