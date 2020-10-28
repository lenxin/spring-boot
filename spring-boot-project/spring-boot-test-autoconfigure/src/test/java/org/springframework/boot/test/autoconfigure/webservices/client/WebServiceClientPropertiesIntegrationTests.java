package org.springframework.boot.test.autoconfigure.webservices.client;

import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Tests for the {@link WebServiceClientTest#properties properties} attribute of
 * {@link WebServiceClientTest @WebServiceClientTest}.
 *

 */
@WebServiceClientTest(properties = "spring.profiles.active=test")
class WebServiceClientPropertiesIntegrationTests {

	@Autowired
	private Environment environment;

	@Test
	void environmentWithNewProfile() {
		assertThat(this.environment.getActiveProfiles()).containsExactly("test");
	}

}
