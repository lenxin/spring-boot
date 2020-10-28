package org.springframework.boot.autoconfigure.data.ldap.person;

import javax.naming.Name;

import org.springframework.ldap.odm.annotations.Attribute;
import org.springframework.ldap.odm.annotations.DnAttribute;
import org.springframework.ldap.odm.annotations.Entry;
import org.springframework.ldap.odm.annotations.Id;

@Entry(objectClasses = { "person", "top" }, base = "ou=someOu")
public class Person {

	@Id
	private Name dn;

	@Attribute(name = "cn")
	@DnAttribute(value = "cn", index = 1)
	private String fullName;

}
