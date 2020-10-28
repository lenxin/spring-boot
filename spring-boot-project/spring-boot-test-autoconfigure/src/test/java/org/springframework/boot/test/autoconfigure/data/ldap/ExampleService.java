package org.springframework.boot.test.autoconfigure.data.ldap;

import org.springframework.ldap.core.LdapTemplate;
import org.springframework.ldap.query.LdapQuery;
import org.springframework.stereotype.Service;

/**
 * Example service used with {@link DataLdapTest @DataLdapTest} tests.
 *

 */
@Service
public class ExampleService {

	private final LdapTemplate ldapTemplate;

	public ExampleService(LdapTemplate ldapTemplate) {
		this.ldapTemplate = ldapTemplate;
	}

	public boolean hasEntry(LdapQuery query) {
		return this.ldapTemplate.find(query, ExampleEntry.class).size() == 1;
	}

}
