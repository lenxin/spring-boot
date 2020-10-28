package org.springframework.boot.actuate.autoconfigure.health;

import org.springframework.boot.actuate.autoconfigure.endpoint.condition.ConditionalOnAvailableEndpoint;
import org.springframework.boot.actuate.health.HealthEndpoint;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

/**
 * {@link EnableAutoConfiguration Auto-configuration} for {@link HealthEndpoint}.
 *




 * @since 2.0.0
 */
@Configuration(proxyBeanMethods = false)
@ConditionalOnAvailableEndpoint(endpoint = HealthEndpoint.class)
@EnableConfigurationProperties(HealthEndpointProperties.class)
@Import({ HealthEndpointConfiguration.class, ReactiveHealthEndpointConfiguration.class,
		HealthEndpointWebExtensionConfiguration.class, HealthEndpointReactiveWebExtensionConfiguration.class })
public class HealthEndpointAutoConfiguration {

}
