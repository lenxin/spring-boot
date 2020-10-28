package org.springframework.boot.actuate.autoconfigure.health;

import org.springframework.boot.actuate.health.HealthContributorRegistry;
import org.springframework.boot.actuate.health.HealthEndpoint;
import org.springframework.boot.actuate.health.HealthEndpointGroups;
import org.springframework.boot.actuate.health.HealthEndpointWebExtension;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication.Type;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Configuration for {@link HealthEndpoint} web extensions.
 *

 * @see HealthEndpointAutoConfiguration
 */
@Configuration(proxyBeanMethods = false)
@ConditionalOnWebApplication(type = Type.SERVLET)
@ConditionalOnBean(HealthEndpoint.class)
class HealthEndpointWebExtensionConfiguration {

	@Bean
	@ConditionalOnBean(HealthEndpoint.class)
	@ConditionalOnMissingBean
	HealthEndpointWebExtension healthEndpointWebExtension(HealthContributorRegistry healthContributorRegistry,
			HealthEndpointGroups groups) {
		return new HealthEndpointWebExtension(healthContributorRegistry, groups);
	}

}
