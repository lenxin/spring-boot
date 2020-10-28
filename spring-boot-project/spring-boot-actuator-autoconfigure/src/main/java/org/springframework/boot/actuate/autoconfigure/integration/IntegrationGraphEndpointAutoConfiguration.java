package org.springframework.boot.actuate.autoconfigure.integration;

import org.springframework.boot.actuate.autoconfigure.endpoint.condition.ConditionalOnAvailableEndpoint;
import org.springframework.boot.actuate.integration.IntegrationGraphEndpoint;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.integration.IntegrationAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.config.IntegrationConfigurationBeanFactoryPostProcessor;
import org.springframework.integration.graph.IntegrationGraphServer;

/**
 * {@link EnableAutoConfiguration Auto-configuration} for the
 * {@link IntegrationGraphEndpoint}.
 *


 * @since 2.1.0
 */
@Configuration(proxyBeanMethods = false)
@ConditionalOnClass(IntegrationGraphServer.class)
@ConditionalOnBean(IntegrationConfigurationBeanFactoryPostProcessor.class)
@ConditionalOnAvailableEndpoint(endpoint = IntegrationGraphEndpoint.class)
@AutoConfigureAfter(IntegrationAutoConfiguration.class)
public class IntegrationGraphEndpointAutoConfiguration {

	@Bean
	@ConditionalOnMissingBean
	public IntegrationGraphEndpoint integrationGraphEndpoint(IntegrationGraphServer integrationGraphServer) {
		return new IntegrationGraphEndpoint(integrationGraphServer);
	}

	@Bean
	@ConditionalOnMissingBean
	public IntegrationGraphServer integrationGraphServer() {
		return new IntegrationGraphServer();
	}

}
