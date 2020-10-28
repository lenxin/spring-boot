package org.springframework.boot.test.autoconfigure.json;

import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Tests for the {@link JsonTest#properties properties} attribute of
 * {@link JsonTest @JsonTest}.
 *

 */
@JsonTest(properties = "spring.profiles.active=test")
class JsonTestPropertiesIntegrationTests {

	@Autowired
	private Environment environment;

	@Test
	void environmentWithNewProfile() {
		assertThat(this.environment.getActiveProfiles()).containsExactly("test");
	}

}
