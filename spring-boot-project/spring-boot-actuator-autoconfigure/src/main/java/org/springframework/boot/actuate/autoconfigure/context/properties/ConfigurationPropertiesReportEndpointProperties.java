package org.springframework.boot.actuate.autoconfigure.context.properties;

import org.springframework.boot.actuate.context.properties.ConfigurationPropertiesReportEndpoint;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * Configuration properties for {@link ConfigurationPropertiesReportEndpoint}.
 *

 * @since 2.0.0
 */
@ConfigurationProperties("management.endpoint.configprops")
public class ConfigurationPropertiesReportEndpointProperties {

	/**
	 * Keys that should be sanitized. Keys can be simple strings that the property ends
	 * with or regular expressions.
	 */
	private String[] keysToSanitize;

	public String[] getKeysToSanitize() {
		return this.keysToSanitize;
	}

	public void setKeysToSanitize(String[] keysToSanitize) {
		this.keysToSanitize = keysToSanitize;
	}

}
