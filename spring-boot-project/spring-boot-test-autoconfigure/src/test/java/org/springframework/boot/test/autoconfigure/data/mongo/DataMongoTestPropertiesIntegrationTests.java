package org.springframework.boot.test.autoconfigure.data.mongo;

import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Tests for the {@link DataMongoTest#properties properties} attribute of
 * {@link DataMongoTest @DataMongoTest}.
 *

 */
@DataMongoTest(properties = "spring.profiles.active=test")
class DataMongoTestPropertiesIntegrationTests {

	@Autowired
	private Environment environment;

	@Test
	void environmentWithNewProfile() {
		assertThat(this.environment.getActiveProfiles()).containsExactly("test");
	}

}
