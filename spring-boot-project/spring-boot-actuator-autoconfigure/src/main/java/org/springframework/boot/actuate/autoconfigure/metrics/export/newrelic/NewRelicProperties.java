package org.springframework.boot.actuate.autoconfigure.metrics.export.newrelic;

import io.micrometer.newrelic.ClientProviderType;

import org.springframework.boot.actuate.autoconfigure.metrics.export.properties.StepRegistryProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * {@link ConfigurationProperties @ConfigurationProperties} for configuring New Relic
 * metrics export.
 *




 * @since 2.0.0
 */
@ConfigurationProperties(prefix = "management.metrics.export.newrelic")
public class NewRelicProperties extends StepRegistryProperties {

	/**
	 * Whether to send the meter name as the event type instead of using the 'event-type'
	 * configuration property value. Can be set to 'true' if New Relic guidelines are not
	 * being followed or event types consistent with previous Spring Boot releases are
	 * required.
	 */
	private boolean meterNameEventTypeEnabled;

	/**
	 * The event type that should be published. This property will be ignored if
	 * 'meter-name-event-type-enabled' is set to 'true'.
	 */
	private String eventType = "SpringBootSample";

	/**
	 * Client provider type to use.
	 */
	private ClientProviderType clientProviderType = ClientProviderType.INSIGHTS_API;

	/**
	 * New Relic API key.
	 */
	private String apiKey;

	/**
	 * New Relic account ID.
	 */
	private String accountId;

	/**
	 * URI to ship metrics to.
	 */
	private String uri = "https://insights-collector.newrelic.com";

	public boolean isMeterNameEventTypeEnabled() {
		return this.meterNameEventTypeEnabled;
	}

	public void setMeterNameEventTypeEnabled(boolean meterNameEventTypeEnabled) {
		this.meterNameEventTypeEnabled = meterNameEventTypeEnabled;
	}

	public String getEventType() {
		return this.eventType;
	}

	public void setEventType(String eventType) {
		this.eventType = eventType;
	}

	public ClientProviderType getClientProviderType() {
		return this.clientProviderType;
	}

	public void setClientProviderType(ClientProviderType clientProviderType) {
		this.clientProviderType = clientProviderType;
	}

	public String getApiKey() {
		return this.apiKey;
	}

	public void setApiKey(String apiKey) {
		this.apiKey = apiKey;
	}

	public String getAccountId() {
		return this.accountId;
	}

	public void setAccountId(String accountId) {
		this.accountId = accountId;
	}

	public String getUri() {
		return this.uri;
	}

	public void setUri(String uri) {
		this.uri = uri;
	}

}
