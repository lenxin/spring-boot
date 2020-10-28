package org.springframework.boot.actuate.autoconfigure.metrics.export.stackdriver;

import org.springframework.boot.actuate.autoconfigure.metrics.export.properties.StepRegistryProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * {@link ConfigurationProperties @ConfigurationProperties} for configuring Stackdriver
 * metrics export.
 *


 * @since 2.3.0
 */
@ConfigurationProperties(prefix = "management.metrics.export.stackdriver")
public class StackdriverProperties extends StepRegistryProperties {

	/**
	 * Identifier of the Google Cloud project to monitor.
	 */
	private String projectId;

	/**
	 * Monitored resource type.
	 */
	private String resourceType = "global";

	public String getProjectId() {
		return this.projectId;
	}

	public void setProjectId(String projectId) {
		this.projectId = projectId;
	}

	public String getResourceType() {
		return this.resourceType;
	}

	public void setResourceType(String resourceType) {
		this.resourceType = resourceType;
	}

}
