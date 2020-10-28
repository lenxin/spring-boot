package org.springframework.boot.test.autoconfigure.data.r2dbc;

import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Tests for the {@link DataR2dbcTest#properties properties} attribute of
 * {@link DataR2dbcTest @DataR2dbcTest}.
 *

 */
@DataR2dbcTest(properties = "spring.profiles.active=test")
class DataR2dbcTestPropertiesIntegrationTests {

	@Autowired
	private Environment environment;

	@Test
	void environmentWithNewProfile() {
		assertThat(this.environment.getActiveProfiles()).containsExactly("test");
	}

}
