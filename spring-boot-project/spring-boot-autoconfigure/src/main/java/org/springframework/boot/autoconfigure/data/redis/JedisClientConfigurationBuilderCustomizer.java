package org.springframework.boot.autoconfigure.data.redis;

import org.springframework.data.redis.connection.jedis.JedisClientConfiguration;
import org.springframework.data.redis.connection.jedis.JedisClientConfiguration.JedisClientConfigurationBuilder;

/**
 * Callback interface that can be implemented by beans wishing to customize the
 * {@link JedisClientConfiguration} via a {@link JedisClientConfigurationBuilder
 * JedisClientConfiguration.JedisClientConfigurationBuilder} whilst retaining default
 * auto-configuration.
 *

 * @since 2.0.0
 */
@FunctionalInterface
public interface JedisClientConfigurationBuilderCustomizer {

	/**
	 * Customize the {@link JedisClientConfigurationBuilder}.
	 * @param clientConfigurationBuilder the builder to customize
	 */
	void customize(JedisClientConfigurationBuilder clientConfigurationBuilder);

}
