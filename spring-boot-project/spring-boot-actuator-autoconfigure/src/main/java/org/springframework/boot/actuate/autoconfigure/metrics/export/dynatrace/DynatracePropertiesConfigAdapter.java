package org.springframework.boot.actuate.autoconfigure.metrics.export.dynatrace;

import io.micrometer.dynatrace.DynatraceConfig;

import org.springframework.boot.actuate.autoconfigure.metrics.export.properties.StepRegistryPropertiesConfigAdapter;

/**
 * Adapter to convert {@link DynatraceProperties} to a {@link DynatraceConfig}.
 *

 */
class DynatracePropertiesConfigAdapter extends StepRegistryPropertiesConfigAdapter<DynatraceProperties>
		implements DynatraceConfig {

	DynatracePropertiesConfigAdapter(DynatraceProperties properties) {
		super(properties);
	}

	@Override
	public String prefix() {
		return "management.metrics.export.dynatrace";
	}

	@Override
	public String apiToken() {
		return get(DynatraceProperties::getApiToken, DynatraceConfig.super::apiToken);
	}

	@Override
	public String deviceId() {
		return get(DynatraceProperties::getDeviceId, DynatraceConfig.super::deviceId);
	}

	@Override
	public String technologyType() {
		return get(DynatraceProperties::getTechnologyType, DynatraceConfig.super::technologyType);
	}

	@Override
	public String uri() {
		return get(DynatraceProperties::getUri, DynatraceConfig.super::uri);
	}

	@Override
	public String group() {
		return get(DynatraceProperties::getGroup, DynatraceConfig.super::group);
	}

}
