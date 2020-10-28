package smoketest.data.ldap;

import org.springframework.data.ldap.repository.LdapRepository;

public interface PersonRepository extends LdapRepository<Person> {

	Person findByPhone(String phone);

}
