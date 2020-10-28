package org.springframework.boot.actuate.autoconfigure.metrics.export.dynatrace;

import org.springframework.boot.actuate.autoconfigure.metrics.export.properties.StepRegistryProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * {@link ConfigurationProperties @ConfigurationProperties} for configuring Dynatrace
 * metrics export.
 *

 * @since 2.1.0
 */
@ConfigurationProperties(prefix = "management.metrics.export.dynatrace")
public class DynatraceProperties extends StepRegistryProperties {

	/**
	 * Dynatrace authentication token.
	 */
	private String apiToken;

	/**
	 * ID of the custom device that is exporting metrics to Dynatrace.
	 */
	private String deviceId;

	/**
	 * Technology type for exported metrics. Used to group metrics under a logical
	 * technology name in the Dynatrace UI.
	 */
	private String technologyType = "java";

	/**
	 * URI to ship metrics to. Should be used for SaaS, self managed instances or to
	 * en-route through an internal proxy.
	 */
	private String uri;

	/**
	 * Group for exported metrics. Used to specify custom device group name in the
	 * Dynatrace UI.
	 */
	private String group;

	public String getApiToken() {
		return this.apiToken;
	}

	public void setApiToken(String apiToken) {
		this.apiToken = apiToken;
	}

	public String getDeviceId() {
		return this.deviceId;
	}

	public void setDeviceId(String deviceId) {
		this.deviceId = deviceId;
	}

	public String getTechnologyType() {
		return this.technologyType;
	}

	public void setTechnologyType(String technologyType) {
		this.technologyType = technologyType;
	}

	public String getUri() {
		return this.uri;
	}

	public void setUri(String uri) {
		this.uri = uri;
	}

	public String getGroup() {
		return this.group;
	}

	public void setGroup(String group) {
		this.group = group;
	}

}
