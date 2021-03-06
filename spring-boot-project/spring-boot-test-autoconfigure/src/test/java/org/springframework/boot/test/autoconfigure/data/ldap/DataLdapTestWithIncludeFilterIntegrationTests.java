package org.springframework.boot.test.autoconfigure.data.ldap;

import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan.Filter;
import org.springframework.ldap.query.LdapQuery;
import org.springframework.ldap.query.LdapQueryBuilder;
import org.springframework.stereotype.Service;
import org.springframework.test.context.TestPropertySource;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Integration test with custom include filter for {@link DataLdapTest @DataLdapTest}.
 *

 */
@DataLdapTest(includeFilters = @Filter(Service.class))
@TestPropertySource(properties = { "spring.ldap.embedded.base-dn=dc=spring,dc=org",
		"spring.ldap.embedded.ldif=classpath:org/springframework/boot/test/autoconfigure/data/ldap/schema.ldif" })
class DataLdapTestWithIncludeFilterIntegrationTests {

	@Autowired
	private ExampleService service;

	@Test
	void testService() {
		LdapQuery ldapQuery = LdapQueryBuilder.query().where("cn").is("Will Smith");
		assertThat(this.service.hasEntry(ldapQuery)).isFalse();
	}

}
