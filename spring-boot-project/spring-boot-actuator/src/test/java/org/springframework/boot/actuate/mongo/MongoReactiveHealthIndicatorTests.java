package org.springframework.boot.actuate.mongo;

import com.mongodb.MongoException;
import org.bson.Document;
import org.junit.jupiter.api.Test;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.Status;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

/**
 * Tests for {@link MongoReactiveHealthIndicator}.
 *

 */
class MongoReactiveHealthIndicatorTests {

	@Test
	void testMongoIsUp() {
		Document buildInfo = mock(Document.class);
		given(buildInfo.getString("version")).willReturn("2.6.4");
		ReactiveMongoTemplate reactiveMongoTemplate = mock(ReactiveMongoTemplate.class);
		given(reactiveMongoTemplate.executeCommand("{ buildInfo: 1 }")).willReturn(Mono.just(buildInfo));
		MongoReactiveHealthIndicator mongoReactiveHealthIndicator = new MongoReactiveHealthIndicator(
				reactiveMongoTemplate);
		Mono<Health> health = mongoReactiveHealthIndicator.health();
		StepVerifier.create(health).consumeNextWith((h) -> {
			assertThat(h.getStatus()).isEqualTo(Status.UP);
			assertThat(h.getDetails()).containsOnlyKeys("version");
			assertThat(h.getDetails().get("version")).isEqualTo("2.6.4");
		}).verifyComplete();
	}

	@Test
	void testMongoIsDown() {
		ReactiveMongoTemplate reactiveMongoTemplate = mock(ReactiveMongoTemplate.class);
		given(reactiveMongoTemplate.executeCommand("{ buildInfo: 1 }"))
				.willThrow(new MongoException("Connection failed"));
		MongoReactiveHealthIndicator mongoReactiveHealthIndicator = new MongoReactiveHealthIndicator(
				reactiveMongoTemplate);
		Mono<Health> health = mongoReactiveHealthIndicator.health();
		StepVerifier.create(health).consumeNextWith((h) -> {
			assertThat(h.getStatus()).isEqualTo(Status.DOWN);
			assertThat(h.getDetails()).containsOnlyKeys("error");
			assertThat(h.getDetails().get("error")).isEqualTo(MongoException.class.getName() + ": Connection failed");
		}).verifyComplete();
	}

}
