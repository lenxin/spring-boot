package org.springframework.boot.autoconfigure.data.alt.ldap;

import javax.naming.Name;

import org.springframework.boot.autoconfigure.data.ldap.person.Person;
import org.springframework.data.repository.Repository;

public interface PersonLdapRepository extends Repository<Person, Name> {

}
