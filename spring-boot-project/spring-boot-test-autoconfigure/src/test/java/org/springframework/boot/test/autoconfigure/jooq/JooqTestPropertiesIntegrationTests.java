package org.springframework.boot.test.autoconfigure.jooq;

import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Tests for the {@link JooqTest#properties properties} attribute of
 * {@link JooqTest @JooqTest}.
 *

 */
@JooqTest(properties = "spring.profiles.active=test")
class JooqTestPropertiesIntegrationTests {

	@Autowired
	private Environment environment;

	@Test
	void environmentWithNewProfile() {
		assertThat(this.environment.getActiveProfiles()).containsExactly("test");
	}

}
