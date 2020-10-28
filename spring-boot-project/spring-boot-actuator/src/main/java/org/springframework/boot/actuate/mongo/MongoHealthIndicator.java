package org.springframework.boot.actuate.mongo;

import org.bson.Document;

import org.springframework.boot.actuate.health.AbstractHealthIndicator;
import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.util.Assert;

/**
 * Simple implementation of a {@link HealthIndicator} returning status information for
 * Mongo data stores.
 *

 * @since 2.0.0
 */
public class MongoHealthIndicator extends AbstractHealthIndicator {

	private final MongoTemplate mongoTemplate;

	public MongoHealthIndicator(MongoTemplate mongoTemplate) {
		super("MongoDB health check failed");
		Assert.notNull(mongoTemplate, "MongoTemplate must not be null");
		this.mongoTemplate = mongoTemplate;
	}

	@Override
	protected void doHealthCheck(Health.Builder builder) throws Exception {
		Document result = this.mongoTemplate.executeCommand("{ buildInfo: 1 }");
		builder.up().withDetail("version", result.getString("version"));
	}

}
