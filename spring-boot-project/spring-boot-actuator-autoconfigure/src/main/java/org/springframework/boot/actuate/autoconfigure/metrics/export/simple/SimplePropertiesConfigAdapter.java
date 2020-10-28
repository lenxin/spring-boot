package org.springframework.boot.actuate.autoconfigure.metrics.export.simple;

import java.time.Duration;

import io.micrometer.core.instrument.simple.CountingMode;
import io.micrometer.core.instrument.simple.SimpleConfig;

import org.springframework.boot.actuate.autoconfigure.metrics.export.properties.PropertiesConfigAdapter;

/**
 * Adapter to convert {@link SimpleProperties} to a {@link SimpleConfig}.
 *

 * @since 2.0.0
 */
public class SimplePropertiesConfigAdapter extends PropertiesConfigAdapter<SimpleProperties> implements SimpleConfig {

	public SimplePropertiesConfigAdapter(SimpleProperties properties) {
		super(properties);
	}

	@Override
	public String prefix() {
		return "management.metrics.export.simple";
	}

	@Override
	public String get(String k) {
		return null;
	}

	@Override
	public Duration step() {
		return get(SimpleProperties::getStep, SimpleConfig.super::step);
	}

	@Override
	public CountingMode mode() {
		return get(SimpleProperties::getMode, SimpleConfig.super::mode);
	}

}
