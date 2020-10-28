package org.springframework.boot.cli.command.init;

/**
 * Provide some basic information about a dependency.
 *

 */
final class Dependency {

	private final String id;

	private final String name;

	private final String description;

	Dependency(String id, String name, String description) {
		this.id = id;
		this.name = name;
		this.description = description;
	}

	String getId() {
		return this.id;
	}

	String getName() {
		return this.name;
	}

	String getDescription() {
		return this.description;
	}

}
