package org.springframework.boot.actuate.autoconfigure.metrics.export.stackdriver;

import io.micrometer.stackdriver.StackdriverConfig;

import org.springframework.boot.actuate.autoconfigure.metrics.export.properties.StepRegistryPropertiesConfigAdapter;

/**
 * Adapter to convert {@link StackdriverProperties} to a {@link StackdriverConfig}.
 *

 * @since 2.3.0
 */
public class StackdriverPropertiesConfigAdapter extends StepRegistryPropertiesConfigAdapter<StackdriverProperties>
		implements StackdriverConfig {

	public StackdriverPropertiesConfigAdapter(StackdriverProperties properties) {
		super(properties);
	}

	@Override
	public String prefix() {
		return "management.metrics.export.stackdriver";
	}

	@Override
	public String projectId() {
		return get(StackdriverProperties::getProjectId, StackdriverConfig.super::projectId);
	}

	@Override
	public String resourceType() {
		return get(StackdriverProperties::getResourceType, StackdriverConfig.super::resourceType);
	}

}
