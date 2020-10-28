package org.springframework.boot.actuate.autoconfigure.metrics.export.dynatrace;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Tests for {@link DynatracePropertiesConfigAdapter}.
 *

 */
class DynatracePropertiesConfigAdapterTests {

	@Test
	void whenPropertiesUriIsSetAdapterUriReturnsIt() {
		DynatraceProperties properties = new DynatraceProperties();
		properties.setUri("https://dynatrace.example.com");
		assertThat(new DynatracePropertiesConfigAdapter(properties).uri()).isEqualTo("https://dynatrace.example.com");
	}

	@Test
	void whenPropertiesApiTokenIsSetAdapterApiTokenReturnsIt() {
		DynatraceProperties properties = new DynatraceProperties();
		properties.setApiToken("123ABC");
		assertThat(new DynatracePropertiesConfigAdapter(properties).apiToken()).isEqualTo("123ABC");
	}

	@Test
	void whenPropertiesDeviceIdIsSetAdapterDeviceIdReturnsIt() {
		DynatraceProperties properties = new DynatraceProperties();
		properties.setDeviceId("dev-1");
		assertThat(new DynatracePropertiesConfigAdapter(properties).deviceId()).isEqualTo("dev-1");
	}

	@Test
	void whenPropertiesTechnologyTypeIsSetAdapterTechnologyTypeReturnsIt() {
		DynatraceProperties properties = new DynatraceProperties();
		properties.setTechnologyType("tech-1");
		assertThat(new DynatracePropertiesConfigAdapter(properties).technologyType()).isEqualTo("tech-1");
	}

	@Test
	void whenPropertiesGroupIsSetAdapterGroupReturnsIt() {
		DynatraceProperties properties = new DynatraceProperties();
		properties.setGroup("group-1");
		assertThat(new DynatracePropertiesConfigAdapter(properties).group()).isEqualTo("group-1");
	}

}
