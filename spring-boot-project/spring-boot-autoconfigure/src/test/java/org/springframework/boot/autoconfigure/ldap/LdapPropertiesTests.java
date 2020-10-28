package org.springframework.boot.autoconfigure.ldap;

import org.junit.jupiter.api.Test;

import org.springframework.boot.autoconfigure.ldap.LdapProperties.Template;
import org.springframework.ldap.core.LdapTemplate;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Tests for {@link LdapProperties}
 *

 */
class LdapPropertiesTests {

	@Test
	void ldapTemplatePropertiesUseConsistentLdapTemplateDefaultValues() {
		Template templateProperties = new LdapProperties().getTemplate();
		LdapTemplate ldapTemplate = new LdapTemplate();
		assertThat(ldapTemplate).hasFieldOrPropertyWithValue("ignorePartialResultException",
				templateProperties.isIgnorePartialResultException());
		assertThat(ldapTemplate).hasFieldOrPropertyWithValue("ignoreNameNotFoundException",
				templateProperties.isIgnoreNameNotFoundException());
		assertThat(ldapTemplate).hasFieldOrPropertyWithValue("ignoreSizeLimitExceededException",
				templateProperties.isIgnoreSizeLimitExceededException());
	}

}
