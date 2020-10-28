package org.springframework.boot.docs.autoconfigure;

/**
 * Sample service.
 *

 */
public class UserService {

	private final String name;

	public UserService(String name) {
		this.name = name;
	}

	public String getName() {
		return this.name;
	}

}
