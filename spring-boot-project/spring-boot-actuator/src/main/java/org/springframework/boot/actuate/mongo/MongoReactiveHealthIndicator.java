package org.springframework.boot.actuate.mongo;

import org.bson.Document;
import reactor.core.publisher.Mono;

import org.springframework.boot.actuate.health.AbstractReactiveHealthIndicator;
import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.ReactiveHealthIndicator;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.util.Assert;

/**
 * A {@link ReactiveHealthIndicator} for Mongo.
 *

 * @since 2.0.0
 */
public class MongoReactiveHealthIndicator extends AbstractReactiveHealthIndicator {

	private final ReactiveMongoTemplate reactiveMongoTemplate;

	public MongoReactiveHealthIndicator(ReactiveMongoTemplate reactiveMongoTemplate) {
		super("Mongo health check failed");
		Assert.notNull(reactiveMongoTemplate, "ReactiveMongoTemplate must not be null");
		this.reactiveMongoTemplate = reactiveMongoTemplate;
	}

	@Override
	protected Mono<Health> doHealthCheck(Health.Builder builder) {
		Mono<Document> buildInfo = this.reactiveMongoTemplate.executeCommand("{ buildInfo: 1 }");
		return buildInfo.map((document) -> up(builder, document));
	}

	private Health up(Health.Builder builder, Document document) {
		return builder.up().withDetail("version", document.getString("version")).build();
	}

}
