package org.springframework.boot.actuate.autoconfigure.env;

import org.springframework.boot.actuate.env.EnvironmentEndpoint;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * Configuration properties for {@link EnvironmentEndpoint}.
 *

 * @since 2.0.0
 */
@ConfigurationProperties("management.endpoint.env")
public class EnvironmentEndpointProperties {

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
