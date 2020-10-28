package org.springframework.boot.context.config;

import org.springframework.boot.diagnostics.AbstractFailureAnalyzer;
import org.springframework.boot.diagnostics.FailureAnalysis;
import org.springframework.boot.origin.Origin;

/**
 * An implementation of {@link AbstractFailureAnalyzer} to analyze failures caused by
 * {@link ConfigDataNotFoundException}.
 *


 */
class ConfigDataNotFoundFailureAnalyzer extends AbstractFailureAnalyzer<ConfigDataNotFoundException> {

	@Override
	protected FailureAnalysis analyze(Throwable rootFailure, ConfigDataNotFoundException cause) {
		ConfigDataLocation location = getLocation(cause);
		Origin origin = Origin.from(location);
		String message = String.format("Config data %s does not exist", cause.getReferenceDescription());
		StringBuilder action = new StringBuilder("Check that the value ");
		if (location != null) {
			action.append(String.format("'%s' ", location));
		}
		if (origin != null) {
			action.append(String.format("at %s ", origin));
		}
		action.append("is correct");
		if (location != null && !location.isOptional()) {
			action.append(String.format(", or prefix it with '%s'", ConfigDataLocation.OPTIONAL_PREFIX));
		}
		return new FailureAnalysis(message, action.toString(), cause);
	}

	private ConfigDataLocation getLocation(ConfigDataNotFoundException cause) {
		if (cause instanceof ConfigDataLocationNotFoundException) {
			return ((ConfigDataLocationNotFoundException) cause).getLocation();
		}
		if (cause instanceof ConfigDataResourceNotFoundException) {
			return ((ConfigDataResourceNotFoundException) cause).getLocation();
		}
		return null;
	}

}
