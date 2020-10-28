package org.springframework.boot.test.autoconfigure.data.jdbc;

import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Tests for the {@link DataJdbcTest#properties properties} attribute of
 * {@link DataJdbcTest @DataJdbcTest}.
 *

 */
@DataJdbcTest(properties = "spring.profiles.active=test")
class DataJdbcTestPropertiesIntegrationTests {

	@Autowired
	private Environment environment;

	@Test
	void environmentWithNewProfile() {
		assertThat(this.environment.getActiveProfiles()).containsExactly("test");
	}

}
