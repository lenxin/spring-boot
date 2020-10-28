package org.springframework.boot.actuate.autoconfigure.metrics.export.appoptics;

import java.time.Duration;

import org.springframework.boot.actuate.autoconfigure.metrics.export.properties.StepRegistryProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * {@link ConfigurationProperties @ConfigurationProperties} for configuring AppOptics
 * metrics export.
 *

 * @since 2.1.0
 */
@ConfigurationProperties(prefix = "management.metrics.export.appoptics")
public class AppOpticsProperties extends StepRegistryProperties {

	/**
	 * URI to ship metrics to.
	 */
	private String uri = "https://api.appoptics.com/v1/measurements";

	/**
	 * AppOptics API token.
	 */
	private String apiToken;

	/**
	 * Tag that will be mapped to "@host" when shipping metrics to AppOptics.
	 */
	private String hostTag = "instance";

	/**
	 * Whether to ship a floored time, useful when sending measurements from multiple
	 * hosts to align them on a given time boundary.
	 */
	private boolean floorTimes;

	/**
	 * Number of measurements per request to use for this backend. If more measurements
	 * are found, then multiple requests will be made.
	 */
	private Integer batchSize = 500;

	/**
	 * Connection timeout for requests to this backend.
	 */
	private Duration connectTimeout = Duration.ofSeconds(5);

	public String getUri() {
		return this.uri;
	}

	public void setUri(String uri) {
		this.uri = uri;
	}

	public String getApiToken() {
		return this.apiToken;
	}

	public void setApiToken(String apiToken) {
		this.apiToken = apiToken;
	}

	public String getHostTag() {
		return this.hostTag;
	}

	public void setHostTag(String hostTag) {
		this.hostTag = hostTag;
	}

	public boolean isFloorTimes() {
		return this.floorTimes;
	}

	public void setFloorTimes(boolean floorTimes) {
		this.floorTimes = floorTimes;
	}

	@Override
	public Integer getBatchSize() {
		return this.batchSize;
	}

	@Override
	public void setBatchSize(Integer batchSize) {
		this.batchSize = batchSize;
	}

	@Override
	public Duration getConnectTimeout() {
		return this.connectTimeout;
	}

	@Override
	public void setConnectTimeout(Duration connectTimeout) {
		this.connectTimeout = connectTimeout;
	}

}
