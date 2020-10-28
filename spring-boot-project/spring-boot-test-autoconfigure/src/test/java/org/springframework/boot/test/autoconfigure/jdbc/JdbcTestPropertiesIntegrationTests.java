package org.springframework.boot.test.autoconfigure.jdbc;

import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Tests for the {@link JdbcTest#properties properties} attribute of
 * {@link JdbcTest @JdbcTest}.
 *

 */
@JdbcTest(properties = "spring.profiles.active=test")
class JdbcTestPropertiesIntegrationTests {

	@Autowired
	private Environment environment;

	@Test
	void environmentWithNewProfile() {
		assertThat(this.environment.getActiveProfiles()).containsExactly("test");
	}

}
