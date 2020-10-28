package org.springframework.boot.actuate.autoconfigure.metrics.export.wavefront;

import io.micrometer.wavefront.WavefrontConfig;

import org.springframework.boot.actuate.autoconfigure.metrics.export.properties.PushRegistryPropertiesConfigAdapter;

/**
 * Adapter to convert {@link WavefrontProperties} to a {@link WavefrontConfig}.
 *

 * @since 2.0.0
 */
public class WavefrontPropertiesConfigAdapter extends PushRegistryPropertiesConfigAdapter<WavefrontProperties>
		implements WavefrontConfig {

	public WavefrontPropertiesConfigAdapter(WavefrontProperties properties) {
		super(properties);
	}

	@Override
	public String prefix() {
		return "management.metrics.export.wavefront";
	}

	@Override
	public String get(String k) {
		return null;
	}

	@Override
	public String uri() {
		return get(this::getUriAsString, WavefrontConfig.DEFAULT_DIRECT::uri);
	}

	@Override
	public String source() {
		return get(WavefrontProperties::getSource, WavefrontConfig.super::source);
	}

	@Override
	public String apiToken() {
		return get(WavefrontProperties::getApiToken, WavefrontConfig.super::apiToken);
	}

	@Override
	public String globalPrefix() {
		return get(WavefrontProperties::getGlobalPrefix, WavefrontConfig.super::globalPrefix);
	}

	private String getUriAsString(WavefrontProperties properties) {
		return (properties.getUri() != null) ? properties.getUri().toString() : null;
	}

}
