package org.springframework.boot.actuate.autoconfigure.jolokia;

import java.util.HashMap;
import java.util.Map;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * Configuration properties for Jolokia.
 *



 * @since 2.0.0
 */
@ConfigurationProperties(prefix = "management.endpoint.jolokia")
public class JolokiaProperties {

	/**
	 * Jolokia settings. Refer to the documentation of Jolokia for more details.
	 */
	private final Map<String, String> config = new HashMap<>();

	public Map<String, String> getConfig() {
		return this.config;
	}

}
