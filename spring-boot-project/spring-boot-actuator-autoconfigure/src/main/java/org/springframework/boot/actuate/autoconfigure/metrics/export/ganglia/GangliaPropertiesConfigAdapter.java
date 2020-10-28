package org.springframework.boot.actuate.autoconfigure.metrics.export.ganglia;

import java.time.Duration;
import java.util.concurrent.TimeUnit;

import info.ganglia.gmetric4j.gmetric.GMetric;
import io.micrometer.ganglia.GangliaConfig;

import org.springframework.boot.actuate.autoconfigure.metrics.export.properties.PropertiesConfigAdapter;

/**
 * Adapter to convert {@link GangliaProperties} to a {@link GangliaConfig}.
 *


 */
class GangliaPropertiesConfigAdapter extends PropertiesConfigAdapter<GangliaProperties> implements GangliaConfig {

	GangliaPropertiesConfigAdapter(GangliaProperties properties) {
		super(properties);
	}

	@Override
	public String prefix() {
		return "management.metrics.export.ganglia";
	}

	@Override
	public String get(String k) {
		return null;
	}

	@Override
	public boolean enabled() {
		return get(GangliaProperties::isEnabled, GangliaConfig.super::enabled);
	}

	@Override
	public Duration step() {
		return get(GangliaProperties::getStep, GangliaConfig.super::step);
	}

	@Override
	@Deprecated
	public TimeUnit rateUnits() {
		return get(GangliaProperties::getRateUnits, GangliaConfig.super::rateUnits);
	}

	@Override
	public TimeUnit durationUnits() {
		return get(GangliaProperties::getDurationUnits, GangliaConfig.super::durationUnits);
	}

	@Override
	@Deprecated
	public String protocolVersion() {
		return get(GangliaProperties::getProtocolVersion, GangliaConfig.super::protocolVersion);
	}

	@Override
	public GMetric.UDPAddressingMode addressingMode() {
		return get(GangliaProperties::getAddressingMode, GangliaConfig.super::addressingMode);
	}

	@Override
	public int ttl() {
		return get(GangliaProperties::getTimeToLive, GangliaConfig.super::ttl);
	}

	@Override
	public String host() {
		return get(GangliaProperties::getHost, GangliaConfig.super::host);
	}

	@Override
	public int port() {
		return get(GangliaProperties::getPort, GangliaConfig.super::port);
	}

}
