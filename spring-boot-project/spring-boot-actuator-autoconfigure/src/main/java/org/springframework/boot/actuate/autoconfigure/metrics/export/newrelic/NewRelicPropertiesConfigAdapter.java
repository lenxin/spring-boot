package org.springframework.boot.actuate.autoconfigure.metrics.export.newrelic;

import io.micrometer.newrelic.ClientProviderType;
import io.micrometer.newrelic.NewRelicConfig;

import org.springframework.boot.actuate.autoconfigure.metrics.export.properties.StepRegistryPropertiesConfigAdapter;

/**
 * Adapter to convert {@link NewRelicProperties} to a {@link NewRelicConfig}.
 *


 * @since 2.0.0
 */
public class NewRelicPropertiesConfigAdapter extends StepRegistryPropertiesConfigAdapter<NewRelicProperties>
		implements NewRelicConfig {

	public NewRelicPropertiesConfigAdapter(NewRelicProperties properties) {
		super(properties);
	}

	@Override
	public String prefix() {
		return "management.metrics.export.newrelic";
	}

	@Override
	public boolean meterNameEventTypeEnabled() {
		return get(NewRelicProperties::isMeterNameEventTypeEnabled, NewRelicConfig.super::meterNameEventTypeEnabled);
	}

	@Override
	public String eventType() {
		return get(NewRelicProperties::getEventType, NewRelicConfig.super::eventType);
	}

	@Override
	public ClientProviderType clientProviderType() {
		return get(NewRelicProperties::getClientProviderType, NewRelicConfig.super::clientProviderType);
	}

	@Override
	public String apiKey() {
		return get(NewRelicProperties::getApiKey, NewRelicConfig.super::apiKey);
	}

	@Override
	public String accountId() {
		return get(NewRelicProperties::getAccountId, NewRelicConfig.super::accountId);
	}

	@Override
	public String uri() {
		return get(NewRelicProperties::getUri, NewRelicConfig.super::uri);
	}

}
