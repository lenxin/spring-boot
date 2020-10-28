package org.springframework.boot.autoconfigure.session;

import org.junit.jupiter.api.Test;

import org.springframework.boot.autoconfigure.AutoConfigurations;
import org.springframework.boot.autoconfigure.data.redis.RedisAutoConfiguration;
import org.springframework.boot.autoconfigure.data.redis.RedisReactiveAutoConfiguration;
import org.springframework.boot.test.context.FilteredClassLoader;
import org.springframework.boot.test.context.assertj.AssertableReactiveWebApplicationContext;
import org.springframework.boot.test.context.runner.ContextConsumer;
import org.springframework.boot.test.context.runner.ReactiveWebApplicationContextRunner;
import org.springframework.session.MapSession;
import org.springframework.session.SaveMode;
import org.springframework.session.data.mongo.ReactiveMongoSessionRepository;
import org.springframework.session.data.redis.ReactiveRedisSessionRepository;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Reactive Redis-specific tests for {@link SessionAutoConfiguration}.
 *



 */
class ReactiveSessionAutoConfigurationRedisTests extends AbstractSessionAutoConfigurationTests {

	protected final ReactiveWebApplicationContextRunner contextRunner = new ReactiveWebApplicationContextRunner()
			.withConfiguration(AutoConfigurations.of(SessionAutoConfiguration.class));

	@Test
	void defaultConfig() {
		this.contextRunner.withPropertyValues("spring.session.store-type=redis")
				.withConfiguration(
						AutoConfigurations.of(RedisAutoConfiguration.class, RedisReactiveAutoConfiguration.class))
				.run(validateSpringSessionUsesRedis("spring:session:", SaveMode.ON_SET_ATTRIBUTE));
	}

	@Test
	void defaultConfigWithUniqueStoreImplementation() {
		this.contextRunner.withClassLoader(new FilteredClassLoader(ReactiveMongoSessionRepository.class))
				.withConfiguration(
						AutoConfigurations.of(RedisAutoConfiguration.class, RedisReactiveAutoConfiguration.class))
				.run(validateSpringSessionUsesRedis("spring:session:", SaveMode.ON_SET_ATTRIBUTE));
	}

	@Test
	void defaultConfigWithCustomTimeout() {
		this.contextRunner.withPropertyValues("spring.session.store-type=redis", "spring.session.timeout=1m")
				.withConfiguration(
						AutoConfigurations.of(RedisAutoConfiguration.class, RedisReactiveAutoConfiguration.class))
				.run((context) -> {
					ReactiveRedisSessionRepository repository = validateSessionRepository(context,
							ReactiveRedisSessionRepository.class);
					assertThat(repository).hasFieldOrPropertyWithValue("defaultMaxInactiveInterval", 60);
				});
	}

	@Test
	void redisSessionStoreWithCustomizations() {
		this.contextRunner
				.withConfiguration(
						AutoConfigurations.of(RedisAutoConfiguration.class, RedisReactiveAutoConfiguration.class))
				.withPropertyValues("spring.session.store-type=redis", "spring.session.redis.namespace=foo",
						"spring.session.redis.save-mode=on-get-attribute")
				.run(validateSpringSessionUsesRedis("foo:", SaveMode.ON_GET_ATTRIBUTE));
	}

	private ContextConsumer<AssertableReactiveWebApplicationContext> validateSpringSessionUsesRedis(String namespace,
			SaveMode saveMode) {
		return (context) -> {
			ReactiveRedisSessionRepository repository = validateSessionRepository(context,
					ReactiveRedisSessionRepository.class);
			assertThat(repository).hasFieldOrPropertyWithValue("defaultMaxInactiveInterval",
					MapSession.DEFAULT_MAX_INACTIVE_INTERVAL_SECONDS);
			assertThat(repository).hasFieldOrPropertyWithValue("namespace", namespace);
			assertThat(repository).hasFieldOrPropertyWithValue("saveMode", saveMode);
		};
	}

}
