package org.springframework.boot.actuate.autoconfigure.metrics.export.appoptics;

import io.micrometer.appoptics.AppOpticsConfig;

import org.springframework.boot.actuate.autoconfigure.metrics.export.properties.StepRegistryPropertiesConfigAdapter;

/**
 * Adapter to convert {@link AppOpticsProperties} to an {@link AppOpticsConfig}.
 *

 */
class AppOpticsPropertiesConfigAdapter extends StepRegistryPropertiesConfigAdapter<AppOpticsProperties>
		implements AppOpticsConfig {

	AppOpticsPropertiesConfigAdapter(AppOpticsProperties properties) {
		super(properties);
	}

	@Override
	public String prefix() {
		return "management.metrics.export.appoptics";
	}

	@Override
	public String uri() {
		return get(AppOpticsProperties::getUri, AppOpticsConfig.super::uri);
	}

	@Override
	public String apiToken() {
		return get(AppOpticsProperties::getApiToken, AppOpticsConfig.super::apiToken);
	}

	@Override
	public String hostTag() {
		return get(AppOpticsProperties::getHostTag, AppOpticsConfig.super::hostTag);
	}

	@Override
	public boolean floorTimes() {
		return get(AppOpticsProperties::isFloorTimes, AppOpticsConfig.super::floorTimes);
	}

}
