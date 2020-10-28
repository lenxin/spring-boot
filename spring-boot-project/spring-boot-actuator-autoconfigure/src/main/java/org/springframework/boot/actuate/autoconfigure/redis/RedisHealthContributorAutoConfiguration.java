package org.springframework.boot.actuate.autoconfigure.redis;

import java.util.Map;

import org.springframework.boot.actuate.autoconfigure.health.CompositeHealthContributorConfiguration;
import org.springframework.boot.actuate.autoconfigure.health.ConditionalOnEnabledHealthIndicator;
import org.springframework.boot.actuate.health.HealthContributor;
import org.springframework.boot.actuate.redis.RedisHealthIndicator;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.data.redis.RedisAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;

/**
 * {@link EnableAutoConfiguration Auto-configuration} for {@link RedisHealthIndicator}.
 *




 * @since 2.1.0
 */
@Configuration(proxyBeanMethods = false)
@ConditionalOnClass(RedisConnectionFactory.class)
@ConditionalOnBean(RedisConnectionFactory.class)
@ConditionalOnEnabledHealthIndicator("redis")
@AutoConfigureAfter({ RedisAutoConfiguration.class, RedisReactiveHealthContributorAutoConfiguration.class })
public class RedisHealthContributorAutoConfiguration
		extends CompositeHealthContributorConfiguration<RedisHealthIndicator, RedisConnectionFactory> {

	@Bean
	@ConditionalOnMissingBean(name = { "redisHealthIndicator", "redisHealthContributor" })
	public HealthContributor redisHealthContributor(Map<String, RedisConnectionFactory> redisConnectionFactories) {
		return createContributor(redisConnectionFactories);
	}

}
