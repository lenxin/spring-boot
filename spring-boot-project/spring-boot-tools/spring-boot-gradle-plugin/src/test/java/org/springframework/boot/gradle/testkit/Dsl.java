package org.springframework.boot.gradle.testkit;

/**
 * The DSLs supported by Gradle and demonstrated in the documentation samples
 */
public enum Dsl {

	GROOVY("Groovy", ".gradle"), KOTLIN("Kotlin", ".gradle.kts");

	private final String name;

	private final String extension;

	Dsl(String name, String extension) {
		this.name = name;
		this.extension = extension;
	}

	public String getName() {
		return this.name;
	}

	String getExtension() {
		return this.extension;
	}

}
