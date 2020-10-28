package org.springframework.boot.actuate.autoconfigure.metrics.export.jmx;

import java.time.Duration;

import io.micrometer.jmx.JmxConfig;

import org.springframework.boot.actuate.autoconfigure.metrics.export.properties.PropertiesConfigAdapter;

/**
 * Adapter to convert {@link JmxProperties} to a {@link JmxConfig}.
 *


 */
class JmxPropertiesConfigAdapter extends PropertiesConfigAdapter<JmxProperties> implements JmxConfig {

	JmxPropertiesConfigAdapter(JmxProperties properties) {
		super(properties);
	}

	@Override
	public String prefix() {
		return "management.metrics.export.jmx";
	}

	@Override
	public String get(String key) {
		return null;
	}

	@Override
	public String domain() {
		return get(JmxProperties::getDomain, JmxConfig.super::domain);
	}

	@Override
	public Duration step() {
		return get(JmxProperties::getStep, JmxConfig.super::step);
	}

}
