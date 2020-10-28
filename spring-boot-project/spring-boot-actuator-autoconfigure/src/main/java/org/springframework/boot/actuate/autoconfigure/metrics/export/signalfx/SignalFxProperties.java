package org.springframework.boot.actuate.autoconfigure.metrics.export.signalfx;

import java.time.Duration;

import org.springframework.boot.actuate.autoconfigure.metrics.export.properties.StepRegistryProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * {@link ConfigurationProperties @ConfigurationProperties} for configuring metrics export
 * to SignalFX.
 *



 * @since 2.0.0
 */
@ConfigurationProperties(prefix = "management.metrics.export.signalfx")
public class SignalFxProperties extends StepRegistryProperties {

	/**
	 * Step size (i.e. reporting frequency) to use.
	 */
	private Duration step = Duration.ofSeconds(10);

	/**
	 * SignalFX access token.
	 */
	private String accessToken;

	/**
	 * URI to ship metrics to.
	 */
	private String uri = "https://ingest.signalfx.com";

	/**
	 * Uniquely identifies the app instance that is publishing metrics to SignalFx.
	 * Defaults to the local host name.
	 */
	private String source;

	@Override
	public Duration getStep() {
		return this.step;
	}

	@Override
	public void setStep(Duration step) {
		this.step = step;
	}

	public String getAccessToken() {
		return this.accessToken;
	}

	public void setAccessToken(String accessToken) {
		this.accessToken = accessToken;
	}

	public String getUri() {
		return this.uri;
	}

	public void setUri(String uri) {
		this.uri = uri;
	}

	public String getSource() {
		return this.source;
	}

	public void setSource(String source) {
		this.source = source;
	}

}
