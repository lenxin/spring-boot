package org.springframework.boot.actuate.autoconfigure.metrics.export.statsd;

import java.time.Duration;

import io.micrometer.statsd.StatsdConfig;
import io.micrometer.statsd.StatsdFlavor;
import io.micrometer.statsd.StatsdProtocol;

import org.springframework.boot.actuate.autoconfigure.metrics.export.properties.PropertiesConfigAdapter;

/**
 * Adapter to convert {@link StatsdProperties} to a {@link StatsdConfig}.
 *

 * @since 2.0.0
 */
public class StatsdPropertiesConfigAdapter extends PropertiesConfigAdapter<StatsdProperties> implements StatsdConfig {

	public StatsdPropertiesConfigAdapter(StatsdProperties properties) {
		super(properties);
	}

	@Override
	public String get(String s) {
		return null;
	}

	@Override
	public String prefix() {
		return "management.metrics.export.statsd";
	}

	@Override
	public StatsdFlavor flavor() {
		return get(StatsdProperties::getFlavor, StatsdConfig.super::flavor);
	}

	@Override
	public boolean enabled() {
		return get(StatsdProperties::isEnabled, StatsdConfig.super::enabled);
	}

	@Override
	public String host() {
		return get(StatsdProperties::getHost, StatsdConfig.super::host);
	}

	@Override
	public int port() {
		return get(StatsdProperties::getPort, StatsdConfig.super::port);
	}

	@Override
	public StatsdProtocol protocol() {
		return get(StatsdProperties::getProtocol, StatsdConfig.super::protocol);
	}

	@Override
	public int maxPacketLength() {
		return get(StatsdProperties::getMaxPacketLength, StatsdConfig.super::maxPacketLength);
	}

	@Override
	public Duration pollingFrequency() {
		return get(StatsdProperties::getPollingFrequency, StatsdConfig.super::pollingFrequency);
	}

	@Override
	public boolean publishUnchangedMeters() {
		return get(StatsdProperties::isPublishUnchangedMeters, StatsdConfig.super::publishUnchangedMeters);
	}

}
