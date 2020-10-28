package org.springframework.boot.test.autoconfigure.web.reactive;

import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Tests for the {@link WebFluxTest#properties properties} attribute of
 * {@link WebFluxTest @WebFluxTest}.
 *

 */
@WebFluxTest(properties = "spring.profiles.active=test")
class WebFluxTestPropertiesIntegrationTests {

	@Autowired
	private Environment environment;

	@Test
	void environmentWithNewProfile() {
		assertThat(this.environment.getActiveProfiles()).containsExactly("test");
	}

}
