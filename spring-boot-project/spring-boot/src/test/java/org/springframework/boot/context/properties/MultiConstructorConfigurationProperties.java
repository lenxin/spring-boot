package org.springframework.boot.context.properties;

/**
 * Class used to test multi-constructor binding. Must be public and have public
 * constructors.
 *

 */
public class MultiConstructorConfigurationProperties {

	private String name;

	private int age;

	public MultiConstructorConfigurationProperties() {
	}

	public MultiConstructorConfigurationProperties(String name) {
		this.name = name;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getAge() {
		return this.age;
	}

	public void setAge(int age) {
		this.age = age;
	}

}
