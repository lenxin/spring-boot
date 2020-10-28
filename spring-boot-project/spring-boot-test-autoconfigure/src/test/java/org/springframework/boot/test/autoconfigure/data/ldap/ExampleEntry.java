package org.springframework.boot.test.autoconfigure.data.ldap;

import javax.naming.Name;

import org.springframework.ldap.odm.annotations.Entry;
import org.springframework.ldap.odm.annotations.Id;

/**
 * Example entry used with {@link DataLdapTest @DataLdapTest} tests.
 *

 */
@Entry(objectClasses = { "person", "top" })
public class ExampleEntry {

	@Id
	private Name dn;

	public Name getDn() {
		return this.dn;
	}

	public void setDn(Name dn) {
		this.dn = dn;
	}

}
