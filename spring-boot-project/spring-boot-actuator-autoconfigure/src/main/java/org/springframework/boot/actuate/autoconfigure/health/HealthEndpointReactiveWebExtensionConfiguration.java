package org.springframework.boot.actuate.autoconfigure.health;

import org.springframework.boot.actuate.health.HealthEndpoint;
import org.springframework.boot.actuate.health.HealthEndpointGroups;
import org.springframework.boot.actuate.health.ReactiveHealthContributorRegistry;
import org.springframework.boot.actuate.health.ReactiveHealthEndpointWebExtension;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication.Type;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Configuration for {@link HealthEndpoint} reactive web extensions.
 *

 * @see HealthEndpointAutoConfiguration
 */
@Configuration(proxyBeanMethods = false)
@ConditionalOnWebApplication(type = Type.REACTIVE)
@ConditionalOnBean(HealthEndpoint.class)
class HealthEndpointReactiveWebExtensionConfiguration {

	@Bean
	@ConditionalOnMissingBean
	@ConditionalOnBean(HealthEndpoint.class)
	ReactiveHealthEndpointWebExtension reactiveHealthEndpointWebExtension(
			ReactiveHealthContributorRegistry reactiveHealthContributorRegistry, HealthEndpointGroups groups) {
		return new ReactiveHealthEndpointWebExtension(reactiveHealthContributorRegistry, groups);
	}

}
