package org.springframework.boot.actuate.autoconfigure.metrics.export.stackdriver;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Tests for {@link StackdriverPropertiesConfigAdapter}.
 *

 */
class StackdriverPropertiesConfigAdapterTests {

	@Test
	void whenPropertiesProjectIdIsSetAdapterProjectIdReturnsIt() {
		StackdriverProperties properties = new StackdriverProperties();
		properties.setProjectId("my-gcp-project-id");
		assertThat(new StackdriverPropertiesConfigAdapter(properties).projectId()).isEqualTo("my-gcp-project-id");
	}

	@Test
	void whenPropertiesResourceTypeIsSetAdapterResourceTypeReturnsIt() {
		StackdriverProperties properties = new StackdriverProperties();
		properties.setResourceType("my-resource-type");
		assertThat(new StackdriverPropertiesConfigAdapter(properties).resourceType()).isEqualTo("my-resource-type");
	}

}
