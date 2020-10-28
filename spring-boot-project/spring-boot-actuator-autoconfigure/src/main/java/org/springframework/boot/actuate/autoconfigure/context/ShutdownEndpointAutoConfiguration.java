package org.springframework.boot.actuate.autoconfigure.context;

import org.springframework.boot.actuate.autoconfigure.endpoint.condition.ConditionalOnAvailableEndpoint;
import org.springframework.boot.actuate.context.ShutdownEndpoint;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * {@link EnableAutoConfiguration Auto-configuration} for the {@link ShutdownEndpoint}.
 *

 * @since 2.0.0
 */
@Configuration(proxyBeanMethods = false)
@ConditionalOnAvailableEndpoint(endpoint = ShutdownEndpoint.class)
public class ShutdownEndpointAutoConfiguration {

	@Bean(destroyMethod = "")
	@ConditionalOnMissingBean
	public ShutdownEndpoint shutdownEndpoint() {
		return new ShutdownEndpoint();
	}

}
