package org.springframework.boot.test.autoconfigure.data.ldap;

import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Tests for the {@link DataLdapTest#properties properties} attribute of
 * {@link DataLdapTest @DataLdapTest}.
 *

 */
@DataLdapTest(properties = "spring.profiles.active=test")
class DataLdapTestPropertiesIntegrationTests {

	@Autowired
	private Environment environment;

	@Test
	void environmentWithNewProfile() {
		assertThat(this.environment.getActiveProfiles()).containsExactly("test");
	}

}
