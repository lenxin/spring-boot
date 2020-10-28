package org.springframework.boot.actuate.autoconfigure.metrics.export.kairos;

import io.micrometer.kairos.KairosConfig;

import org.springframework.boot.actuate.autoconfigure.metrics.export.properties.StepRegistryPropertiesConfigAdapter;

/**
 * Adapter to convert {@link KairosProperties} to a {@link KairosConfig}.
 *

 */
class KairosPropertiesConfigAdapter extends StepRegistryPropertiesConfigAdapter<KairosProperties>
		implements KairosConfig {

	KairosPropertiesConfigAdapter(KairosProperties properties) {
		super(properties);
	}

	@Override
	public String prefix() {
		return "management.metrics.export.kairos";
	}

	@Override
	public String uri() {
		return get(KairosProperties::getUri, KairosConfig.super::uri);
	}

	@Override
	public String userName() {
		return get(KairosProperties::getUserName, KairosConfig.super::userName);
	}

	@Override
	public String password() {
		return get(KairosProperties::getPassword, KairosConfig.super::password);
	}

}
