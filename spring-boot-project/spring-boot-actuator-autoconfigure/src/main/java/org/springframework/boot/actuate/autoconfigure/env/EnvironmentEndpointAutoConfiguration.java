package org.springframework.boot.actuate.autoconfigure.env;

import org.springframework.boot.actuate.autoconfigure.endpoint.condition.ConditionalOnAvailableEndpoint;
import org.springframework.boot.actuate.env.EnvironmentEndpoint;
import org.springframework.boot.actuate.env.EnvironmentEndpointWebExtension;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

/**
 * {@link EnableAutoConfiguration Auto-configuration} for the {@link EnvironmentEndpoint}.
 *


 * @since 2.0.0
 */
@Configuration(proxyBeanMethods = false)
@ConditionalOnAvailableEndpoint(endpoint = EnvironmentEndpoint.class)
@EnableConfigurationProperties(EnvironmentEndpointProperties.class)
public class EnvironmentEndpointAutoConfiguration {

	@Bean
	@ConditionalOnMissingBean
	public EnvironmentEndpoint environmentEndpoint(Environment environment, EnvironmentEndpointProperties properties) {
		EnvironmentEndpoint endpoint = new EnvironmentEndpoint(environment);
		String[] keysToSanitize = properties.getKeysToSanitize();
		if (keysToSanitize != null) {
			endpoint.setKeysToSanitize(keysToSanitize);
		}
		return endpoint;
	}

	@Bean
	@ConditionalOnMissingBean
	@ConditionalOnBean(EnvironmentEndpoint.class)
	public EnvironmentEndpointWebExtension environmentEndpointWebExtension(EnvironmentEndpoint environmentEndpoint) {
		return new EnvironmentEndpointWebExtension(environmentEndpoint);
	}

}
