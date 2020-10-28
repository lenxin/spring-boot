package org.springframework.boot.actuate.autoconfigure.metrics.export.humio;

import java.time.Duration;
import java.util.HashMap;
import java.util.Map;

import org.springframework.boot.actuate.autoconfigure.metrics.export.properties.StepRegistryProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * {@link ConfigurationProperties @ConfigurationProperties} for configuring Humio metrics
 * export.
 *

 * @since 2.1.0
 */
@ConfigurationProperties(prefix = "management.metrics.export.humio")
public class HumioProperties extends StepRegistryProperties {

	/**
	 * Humio API token.
	 */
	private String apiToken;

	/**
	 * Connection timeout for requests to this backend.
	 */
	private Duration connectTimeout = Duration.ofSeconds(5);

	/**
	 * Humio tags describing the data source in which metrics will be stored. Humio tags
	 * are a distinct concept from Micrometer's tags. Micrometer's tags are used to divide
	 * metrics along dimensional boundaries.
	 */
	private Map<String, String> tags = new HashMap<>();

	/**
	 * URI to ship metrics to. If you need to publish metrics to an internal proxy
	 * en-route to Humio, you can define the location of the proxy with this.
	 */
	private String uri = "https://cloud.humio.com";

	public String getApiToken() {
		return this.apiToken;
	}

	public void setApiToken(String apiToken) {
		this.apiToken = apiToken;
	}

	@Override
	public Duration getConnectTimeout() {
		return this.connectTimeout;
	}

	@Override
	public void setConnectTimeout(Duration connectTimeout) {
		this.connectTimeout = connectTimeout;
	}

	public Map<String, String> getTags() {
		return this.tags;
	}

	public void setTags(Map<String, String> tags) {
		this.tags = tags;
	}

	public String getUri() {
		return this.uri;
	}

	public void setUri(String uri) {
		this.uri = uri;
	}

}
