package org.springframework.boot.autoconfigure.hazelcast;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.core.io.Resource;
import org.springframework.util.Assert;

/**
 * Configuration properties for the hazelcast integration.
 *

 * @since 1.3.0
 */
@ConfigurationProperties(prefix = "spring.hazelcast")
public class HazelcastProperties {

	/**
	 * The location of the configuration file to use to initialize Hazelcast.
	 */
	private Resource config;

	public Resource getConfig() {
		return this.config;
	}

	public void setConfig(Resource config) {
		this.config = config;
	}

	/**
	 * Resolve the config location if set.
	 * @return the location or {@code null} if it is not set
	 * @throws IllegalArgumentException if the config attribute is set to an unknown
	 * location
	 */
	public Resource resolveConfigLocation() {
		if (this.config == null) {
			return null;
		}
		Assert.isTrue(this.config.exists(),
				() -> "Hazelcast configuration does not exist '" + this.config.getDescription() + "'");
		return this.config;
	}

}
