package org.springframework.boot.actuate.autoconfigure.metrics.export.humio;

import java.util.Map;

import io.micrometer.humio.HumioConfig;

import org.springframework.boot.actuate.autoconfigure.metrics.export.properties.StepRegistryPropertiesConfigAdapter;

/**
 * Adapter to convert {@link HumioProperties} to a {@link HumioConfig}.
 *

 */
class HumioPropertiesConfigAdapter extends StepRegistryPropertiesConfigAdapter<HumioProperties> implements HumioConfig {

	HumioPropertiesConfigAdapter(HumioProperties properties) {
		super(properties);
	}

	@Override
	public String prefix() {
		return "management.metrics.export.humio";
	}

	@Override
	public String get(String k) {
		return null;
	}

	@Override
	public String uri() {
		return get(HumioProperties::getUri, HumioConfig.super::uri);
	}

	@Override
	public Map<String, String> tags() {
		return get(HumioProperties::getTags, HumioConfig.super::tags);
	}

	@Override
	public String apiToken() {
		return get(HumioProperties::getApiToken, HumioConfig.super::apiToken);
	}

}
