package org.springframework.boot.actuate.autoconfigure.beans;

import org.springframework.boot.actuate.autoconfigure.endpoint.condition.ConditionalOnAvailableEndpoint;
import org.springframework.boot.actuate.beans.BeansEndpoint;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * {@link EnableAutoConfiguration Auto-configuration} for the {@link BeansEndpoint}.
 *

 * @since 2.0.0
 */
@Configuration(proxyBeanMethods = false)
@ConditionalOnAvailableEndpoint(endpoint = BeansEndpoint.class)
public class BeansEndpointAutoConfiguration {

	@Bean
	@ConditionalOnMissingBean
	public BeansEndpoint beansEndpoint(ConfigurableApplicationContext applicationContext) {
		return new BeansEndpoint(applicationContext);
	}

}
