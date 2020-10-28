package org.springframework.boot.actuate.autoconfigure.metrics.export.prometheus;

import java.time.Duration;

import io.micrometer.prometheus.HistogramFlavor;
import io.micrometer.prometheus.PrometheusConfig;

import org.springframework.boot.actuate.autoconfigure.metrics.export.properties.PropertiesConfigAdapter;

/**
 * Adapter to convert {@link PrometheusProperties} to a {@link PrometheusConfig}.
 *


 */
class PrometheusPropertiesConfigAdapter extends PropertiesConfigAdapter<PrometheusProperties>
		implements PrometheusConfig {

	PrometheusPropertiesConfigAdapter(PrometheusProperties properties) {
		super(properties);
	}

	@Override
	public String prefix() {
		return "management.metrics.export.prometheus";
	}

	@Override
	public String get(String key) {
		return null;
	}

	@Override
	public boolean descriptions() {
		return get(PrometheusProperties::isDescriptions, PrometheusConfig.super::descriptions);
	}

	@Override
	public HistogramFlavor histogramFlavor() {
		return get(PrometheusProperties::getHistogramFlavor, PrometheusConfig.super::histogramFlavor);
	}

	@Override
	public Duration step() {
		return get(PrometheusProperties::getStep, PrometheusConfig.super::step);
	}

}
