package org.springframework.boot.actuate.autoconfigure.endpoint.web;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;

/**
 * Tests for {@link WebEndpointProperties}.
 *

 */
class WebEndpointPropertiesTests {

	@Test
	void defaultBasePathShouldBeApplication() {
		WebEndpointProperties properties = new WebEndpointProperties();
		assertThat(properties.getBasePath()).isEqualTo("/actuator");
	}

	@Test
	void basePathShouldBeCleaned() {
		WebEndpointProperties properties = new WebEndpointProperties();
		properties.setBasePath("/");
		assertThat(properties.getBasePath()).isEqualTo("");
		properties.setBasePath("/actuator/");
		assertThat(properties.getBasePath()).isEqualTo("/actuator");
	}

	@Test
	void basePathMustStartWithSlash() {
		WebEndpointProperties properties = new WebEndpointProperties();
		assertThatIllegalArgumentException().isThrownBy(() -> properties.setBasePath("admin"))
				.withMessageContaining("Base path must start with '/' or be empty");
	}

	@Test
	void basePathCanBeEmpty() {
		WebEndpointProperties properties = new WebEndpointProperties();
		properties.setBasePath("");
		assertThat(properties.getBasePath()).isEqualTo("");
	}

}
