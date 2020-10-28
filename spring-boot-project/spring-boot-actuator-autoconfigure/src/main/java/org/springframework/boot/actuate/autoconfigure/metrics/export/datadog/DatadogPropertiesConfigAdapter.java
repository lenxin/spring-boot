package org.springframework.boot.actuate.autoconfigure.metrics.export.datadog;

import io.micrometer.datadog.DatadogConfig;

import org.springframework.boot.actuate.autoconfigure.metrics.export.properties.StepRegistryPropertiesConfigAdapter;

/**
 * Adapter to convert {@link DatadogProperties} to a {@link DatadogConfig}.
 *


 */
class DatadogPropertiesConfigAdapter extends StepRegistryPropertiesConfigAdapter<DatadogProperties>
		implements DatadogConfig {

	DatadogPropertiesConfigAdapter(DatadogProperties properties) {
		super(properties);
	}

	@Override
	public String prefix() {
		return "management.metrics.export.datadog";
	}

	@Override
	public String apiKey() {
		return get(DatadogProperties::getApiKey, DatadogConfig.super::apiKey);
	}

	@Override
	public String applicationKey() {
		return get(DatadogProperties::getApplicationKey, DatadogConfig.super::applicationKey);
	}

	@Override
	public String hostTag() {
		return get(DatadogProperties::getHostTag, DatadogConfig.super::hostTag);
	}

	@Override
	public String uri() {
		return get(DatadogProperties::getUri, DatadogConfig.super::uri);
	}

	@Override
	public boolean descriptions() {
		return get(DatadogProperties::isDescriptions, DatadogConfig.super::descriptions);
	}

}
