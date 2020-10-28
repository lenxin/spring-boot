package org.springframework.boot.autoconfigure.data.redis;

import org.springframework.data.redis.connection.lettuce.LettuceClientConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceClientConfiguration.LettuceClientConfigurationBuilder;

/**
 * Callback interface that can be implemented by beans wishing to customize the
 * {@link LettuceClientConfiguration} via a {@link LettuceClientConfigurationBuilder
 * LettuceClientConfiguration.LettuceClientConfigurationBuilder} whilst retaining default
 * auto-configuration.
 *

 * @since 2.0.0
 */
@FunctionalInterface
public interface LettuceClientConfigurationBuilderCustomizer {

	/**
	 * Customize the {@link LettuceClientConfigurationBuilder}.
	 * @param clientConfigurationBuilder the builder to customize
	 */
	void customize(LettuceClientConfigurationBuilder clientConfigurationBuilder);

}
