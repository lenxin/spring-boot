package org.springframework.boot.actuate.autoconfigure.context.properties;

import org.springframework.boot.actuate.autoconfigure.endpoint.condition.ConditionalOnAvailableEndpoint;
import org.springframework.boot.actuate.context.properties.ConfigurationPropertiesReportEndpoint;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * {@link EnableAutoConfiguration Auto-configuration} for the
 * {@link ConfigurationPropertiesReportEndpoint}.
 *


 * @since 2.0.0
 */
@Configuration(proxyBeanMethods = false)
@ConditionalOnAvailableEndpoint(endpoint = ConfigurationPropertiesReportEndpoint.class)
@EnableConfigurationProperties(ConfigurationPropertiesReportEndpointProperties.class)
public class ConfigurationPropertiesReportEndpointAutoConfiguration {

	@Bean
	@ConditionalOnMissingBean
	public ConfigurationPropertiesReportEndpoint configurationPropertiesReportEndpoint(
			ConfigurationPropertiesReportEndpointProperties properties) {
		ConfigurationPropertiesReportEndpoint endpoint = new ConfigurationPropertiesReportEndpoint();
		String[] keysToSanitize = properties.getKeysToSanitize();
		if (keysToSanitize != null) {
			endpoint.setKeysToSanitize(keysToSanitize);
		}
		return endpoint;
	}

}
