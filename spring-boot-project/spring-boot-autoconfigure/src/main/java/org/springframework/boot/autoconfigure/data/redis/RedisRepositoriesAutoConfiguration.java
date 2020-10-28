package org.springframework.boot.autoconfigure.data.redis;

import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.repository.configuration.EnableRedisRepositories;
import org.springframework.data.redis.repository.support.RedisRepositoryFactoryBean;

/**
 * {@link EnableAutoConfiguration Auto-configuration} for Spring Data's Redis
 * Repositories.
 *


 * @see EnableRedisRepositories
 * @since 1.4.0
 */
@Configuration(proxyBeanMethods = false)
@ConditionalOnClass(EnableRedisRepositories.class)
@ConditionalOnBean(RedisConnectionFactory.class)
@ConditionalOnProperty(prefix = "spring.data.redis.repositories", name = "enabled", havingValue = "true",
		matchIfMissing = true)
@ConditionalOnMissingBean(RedisRepositoryFactoryBean.class)
@Import(RedisRepositoriesRegistrar.class)
@AutoConfigureAfter(RedisAutoConfiguration.class)
public class RedisRepositoriesAutoConfiguration {

}
