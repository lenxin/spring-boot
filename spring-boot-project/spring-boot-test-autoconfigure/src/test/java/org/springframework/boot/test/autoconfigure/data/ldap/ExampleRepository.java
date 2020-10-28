package org.springframework.boot.test.autoconfigure.data.ldap;

import org.springframework.data.ldap.repository.LdapRepository;

/**
 * Example repository used with {@link DataLdapTest @DataLdapTest} tests.
 *

 */
interface ExampleRepository extends LdapRepository<ExampleEntry> {

}
