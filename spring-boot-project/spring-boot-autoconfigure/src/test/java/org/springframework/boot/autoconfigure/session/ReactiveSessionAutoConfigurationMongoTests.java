package org.springframework.boot.autoconfigure.session;

import org.junit.jupiter.api.Test;

import org.springframework.boot.autoconfigure.AutoConfigurations;
import org.springframework.boot.autoconfigure.data.mongo.MongoDataAutoConfiguration;
import org.springframework.boot.autoconfigure.data.mongo.MongoReactiveDataAutoConfiguration;
import org.springframework.boot.autoconfigure.mongo.MongoAutoConfiguration;
import org.springframework.boot.autoconfigure.mongo.MongoReactiveAutoConfiguration;
import org.springframework.boot.autoconfigure.mongo.embedded.EmbeddedMongoAutoConfiguration;
import org.springframework.boot.test.context.FilteredClassLoader;
import org.springframework.boot.test.context.assertj.AssertableReactiveWebApplicationContext;
import org.springframework.boot.test.context.runner.ContextConsumer;
import org.springframework.boot.test.context.runner.ReactiveWebApplicationContextRunner;
import org.springframework.session.data.mongo.ReactiveMongoSessionRepository;
import org.springframework.session.data.redis.ReactiveRedisSessionRepository;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Mongo-specific tests for {@link SessionAutoConfiguration}.
 *

 */
class ReactiveSessionAutoConfigurationMongoTests extends AbstractSessionAutoConfigurationTests {

	private final ReactiveWebApplicationContextRunner contextRunner = new ReactiveWebApplicationContextRunner()
			.withConfiguration(AutoConfigurations.of(SessionAutoConfiguration.class));

	@Test
	void defaultConfig() {
		this.contextRunner.withPropertyValues("spring.session.store-type=mongodb")
				.withConfiguration(AutoConfigurations.of(EmbeddedMongoAutoConfiguration.class,
						MongoAutoConfiguration.class, MongoDataAutoConfiguration.class,
						MongoReactiveAutoConfiguration.class, MongoReactiveDataAutoConfiguration.class))
				.run(validateSpringSessionUsesMongo("sessions"));
	}

	@Test
	void defaultConfigWithUniqueStoreImplementation() {
		this.contextRunner.withClassLoader(new FilteredClassLoader(ReactiveRedisSessionRepository.class))
				.withConfiguration(AutoConfigurations.of(EmbeddedMongoAutoConfiguration.class,
						MongoAutoConfiguration.class, MongoDataAutoConfiguration.class,
						MongoReactiveAutoConfiguration.class, MongoReactiveDataAutoConfiguration.class))
				.run(validateSpringSessionUsesMongo("sessions"));
	}

	@Test
	void defaultConfigWithCustomTimeout() {
		this.contextRunner.withPropertyValues("spring.session.store-type=mongodb", "spring.session.timeout=1m")
				.withConfiguration(AutoConfigurations.of(EmbeddedMongoAutoConfiguration.class,
						MongoAutoConfiguration.class, MongoDataAutoConfiguration.class,
						MongoReactiveAutoConfiguration.class, MongoReactiveDataAutoConfiguration.class))
				.run((context) -> {
					ReactiveMongoSessionRepository repository = validateSessionRepository(context,
							ReactiveMongoSessionRepository.class);
					assertThat(repository).hasFieldOrPropertyWithValue("maxInactiveIntervalInSeconds", 60);
				});
	}

	@Test
	void mongoSessionStoreWithCustomizations() {
		this.contextRunner
				.withConfiguration(AutoConfigurations.of(EmbeddedMongoAutoConfiguration.class,
						MongoAutoConfiguration.class, MongoDataAutoConfiguration.class,
						MongoReactiveAutoConfiguration.class, MongoReactiveDataAutoConfiguration.class))
				.withPropertyValues("spring.session.store-type=mongodb", "spring.session.mongodb.collection-name=foo")
				.run(validateSpringSessionUsesMongo("foo"));
	}

	private ContextConsumer<AssertableReactiveWebApplicationContext> validateSpringSessionUsesMongo(
			String collectionName) {
		return (context) -> {
			ReactiveMongoSessionRepository repository = validateSessionRepository(context,
					ReactiveMongoSessionRepository.class);
			assertThat(repository.getCollectionName()).isEqualTo(collectionName);
			assertThat(repository).hasFieldOrPropertyWithValue("maxInactiveIntervalInSeconds",
					ReactiveMongoSessionRepository.DEFAULT_INACTIVE_INTERVAL);
		};
	}

}
