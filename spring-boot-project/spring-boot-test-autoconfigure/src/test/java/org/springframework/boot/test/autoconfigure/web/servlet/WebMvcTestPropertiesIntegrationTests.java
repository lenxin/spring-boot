package org.springframework.boot.test.autoconfigure.web.servlet;

import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Tests for the {@link WebMvcTest#properties properties} attribute of
 * {@link WebMvcTest @WebMvcTest}.
 *

 */
@WebMvcTest(properties = "spring.profiles.active=test")
class WebMvcTestPropertiesIntegrationTests {

	@Autowired
	private Environment environment;

	@Test
	void environmentWithNewProfile() {
		assertThat(this.environment.getActiveProfiles()).containsExactly("test");
	}

}
