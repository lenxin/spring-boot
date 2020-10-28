package org.springframework.boot.actuate.autoconfigure.metrics.export.appoptics;

import org.junit.jupiter.api.Test;

import org.springframework.boot.actuate.autoconfigure.metrics.export.properties.StepRegistryPropertiesConfigAdapterTests;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Tests for {@link AppOpticsPropertiesConfigAdapter}.
 *

 */
class AppOpticsPropertiesConfigAdapterTests
		extends StepRegistryPropertiesConfigAdapterTests<AppOpticsProperties, AppOpticsPropertiesConfigAdapter> {

	@Override
	protected AppOpticsProperties createProperties() {
		return new AppOpticsProperties();
	}

	@Override
	protected AppOpticsPropertiesConfigAdapter createConfigAdapter(AppOpticsProperties properties) {
		return new AppOpticsPropertiesConfigAdapter(properties);
	}

	@Test
	void whenPropertiesUriIsSetAdapterUriReturnsIt() {
		AppOpticsProperties properties = createProperties();
		properties.setUri("https://appoptics.example.com/v1/measurements");
		assertThat(createConfigAdapter(properties).uri()).isEqualTo("https://appoptics.example.com/v1/measurements");
	}

	@Test
	void whenPropertiesApiTokenIsSetAdapterApiTokenReturnsIt() {
		AppOpticsProperties properties = createProperties();
		properties.setApiToken("ABC123");
		assertThat(createConfigAdapter(properties).apiToken()).isEqualTo("ABC123");
	}

	@Test
	void whenPropertiesHostTagIsSetAdapterHostTagReturnsIt() {
		AppOpticsProperties properties = createProperties();
		properties.setHostTag("node");
		assertThat(createConfigAdapter(properties).hostTag()).isEqualTo("node");
	}

	@Test
	void whenPropertiesFloorTimesIsSetAdapterFloorTimesReturnsIt() {
		AppOpticsProperties properties = createProperties();
		properties.setFloorTimes(true);
		assertThat(createConfigAdapter(properties).floorTimes()).isTrue();
	}

}
