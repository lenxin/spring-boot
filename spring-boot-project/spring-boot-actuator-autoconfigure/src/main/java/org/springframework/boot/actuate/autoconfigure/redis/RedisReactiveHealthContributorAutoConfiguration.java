package org.springframework.boot.actuate.autoconfigure.redis;

import java.util.Map;

import reactor.core.publisher.Flux;

import org.springframework.boot.actuate.autoconfigure.health.CompositeReactiveHealthContributorConfiguration;
import org.springframework.boot.actuate.autoconfigure.health.ConditionalOnEnabledHealthIndicator;
import org.springframework.boot.actuate.health.ReactiveHealthContributor;
import org.springframework.boot.actuate.redis.RedisReactiveHealthIndicator;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.data.redis.RedisReactiveAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.ReactiveRedisConnectionFactory;

/**
 * {@link EnableAutoConfiguration Auto-configuration} for
 * {@link RedisReactiveHealthIndicator}.
 *




 * @since 2.1.0
 */
@Configuration(proxyBeanMethods = false)
@ConditionalOnClass({ ReactiveRedisConnectionFactory.class, Flux.class })
@ConditionalOnBean(ReactiveRedisConnectionFactory.class)
@ConditionalOnEnabledHealthIndicator("redis")
@AutoConfigureAfter(RedisReactiveAutoConfiguration.class)
public class RedisReactiveHealthContributorAutoConfiguration extends
		CompositeReactiveHealthContributorConfiguration<RedisReactiveHealthIndicator, ReactiveRedisConnectionFactory> {

	private final Map<String, ReactiveRedisConnectionFactory> redisConnectionFactories;

	RedisReactiveHealthContributorAutoConfiguration(
			Map<String, ReactiveRedisConnectionFactory> redisConnectionFactories) {
		this.redisConnectionFactories = redisConnectionFactories;
	}

	@Bean
	@ConditionalOnMissingBean(name = { "redisHealthIndicator", "redisHealthContributor" })
	public ReactiveHealthContributor redisHealthContributor() {
		return createContributor(this.redisConnectionFactories);
	}

}
