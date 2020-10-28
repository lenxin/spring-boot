package org.springframework.boot.actuate.autoconfigure.metrics.export.wavefront;

import java.net.URI;

import org.junit.jupiter.api.Test;

import org.springframework.boot.actuate.autoconfigure.metrics.export.properties.PushRegistryPropertiesConfigAdapterTests;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Tests for {@link WavefrontPropertiesConfigAdapter}.
 *

 */
class WavefrontPropertiesConfigAdapterTests
		extends PushRegistryPropertiesConfigAdapterTests<WavefrontProperties, WavefrontPropertiesConfigAdapter> {

	@Override
	protected WavefrontProperties createProperties() {
		return new WavefrontProperties();
	}

	@Override
	protected WavefrontPropertiesConfigAdapter createConfigAdapter(WavefrontProperties properties) {
		return new WavefrontPropertiesConfigAdapter(properties);
	}

	@Test
	void whenPropertiesUriIsSetAdapterUriReturnsIt() {
		WavefrontProperties properties = createProperties();
		properties.setUri(URI.create("https://wavefront.example.com"));
		assertThat(createConfigAdapter(properties).uri()).isEqualTo("https://wavefront.example.com");
	}

	@Test
	void whenPropertiesSourceIsSetAdapterSourceReturnsIt() {
		WavefrontProperties properties = createProperties();
		properties.setSource("test");
		assertThat(createConfigAdapter(properties).source()).isEqualTo("test");
	}

	@Test
	void whenPropertiesApiTokenIsSetAdapterApiTokenReturnsIt() {
		WavefrontProperties properties = createProperties();
		properties.setApiToken("ABC123");
		assertThat(createConfigAdapter(properties).apiToken()).isEqualTo("ABC123");
	}

	@Test
	void whenPropertiesGlobalPrefixIsSetAdapterGlobalPrefixReturnsIt() {
		WavefrontProperties properties = createProperties();
		properties.setGlobalPrefix("test");
		assertThat(createConfigAdapter(properties).globalPrefix()).isEqualTo("test");
	}

}
