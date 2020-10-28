package org.springframework.boot.test.autoconfigure.orm.jpa;

import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.data.repository.config.BootstrapMode;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Tests for non-default attributes of {@link DataJpaTest @DataJpaTest}.
 *


 */
@DataJpaTest(properties = "spring.profiles.active=test", bootstrapMode = BootstrapMode.DEFERRED)
class DataJpaTestAttributesIntegrationTests {

	@Autowired
	private Environment environment;

	@Test
	void environmentWithNewProfile() {
		assertThat(this.environment.getActiveProfiles()).containsExactly("test");
	}

	@Test
	void bootstrapModeIsSet() {
		assertThat(this.environment.getProperty("spring.data.jpa.repositories.bootstrap-mode"))
				.isEqualTo(BootstrapMode.DEFERRED.name());
	}

}
