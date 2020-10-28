package org.springframework.boot.autoconfigure.webservices;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;

/**
 * Tests for {@link WebServicesProperties}.
 *

 */
class WebServicesPropertiesTests {

	private WebServicesProperties properties;

	@Test
	void pathMustNotBeEmpty() {
		this.properties = new WebServicesProperties();
		assertThatIllegalArgumentException().isThrownBy(() -> this.properties.setPath(""))
				.withMessageContaining("Path must have length greater than 1");
	}

	@Test
	void pathMustHaveLengthGreaterThanOne() {
		this.properties = new WebServicesProperties();
		assertThatIllegalArgumentException().isThrownBy(() -> this.properties.setPath("/"))
				.withMessageContaining("Path must have length greater than 1");
	}

	@Test
	void customPathMustBeginWithASlash() {
		this.properties = new WebServicesProperties();
		assertThatIllegalArgumentException().isThrownBy(() -> this.properties.setPath("custom"))
				.withMessageContaining("Path must start with '/'");
	}

}
