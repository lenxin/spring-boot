package org.springframework.boot.context.config;

/**
 * Result returned from {@link ConfigDataLocationResolvers} containing both the
 * {@link ConfigDataResource} and the original {@link ConfigDataLocation}.
 *

 */
class ConfigDataResolutionResult {

	private final ConfigDataLocation location;

	private final ConfigDataResource resource;

	ConfigDataResolutionResult(ConfigDataLocation location, ConfigDataResource resource) {
		this.location = location;
		this.resource = resource;
	}

	ConfigDataLocation getLocation() {
		return this.location;
	}

	ConfigDataResource getResource() {
		return this.resource;
	}

}
