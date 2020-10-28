package org.springframework.boot.system;

import java.lang.invoke.MethodHandles;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.springframework.util.ClassUtils;

/**
 * Known Java versions.
 *


 * @since 2.0.0
 */
public enum JavaVersion {

	/**
	 * Java 1.8.
	 */
	EIGHT("1.8", Optional.class, "empty"),

	/**
	 * Java 9.
	 */
	NINE("9", Optional.class, "stream"),

	/**
	 * Java 10.
	 */
	TEN("10", Optional.class, "orElseThrow"),

	/**
	 * Java 11.
	 */
	ELEVEN("11", String.class, "strip"),

	/**
	 * Java 12.
	 */
	TWELVE("12", String.class, "describeConstable"),

	/**
	 * Java 13.
	 */
	THIRTEEN("13", String.class, "stripIndent"),

	/**
	 * Java 14.
	 */
	FOURTEEN("14", MethodHandles.Lookup.class, "hasFullPrivilegeAccess"),

	/**
	 * Java 15.
	 */
	FIFTEEN("15", CharSequence.class, "isEmpty");

	private final String name;

	private final boolean available;

	JavaVersion(String name, Class<?> clazz, String methodName) {
		this.name = name;
		this.available = ClassUtils.hasMethod(clazz, methodName);
	}

	@Override
	public String toString() {
		return this.name;
	}

	/**
	 * Returns the {@link JavaVersion} of the current runtime.
	 * @return the {@link JavaVersion}
	 */
	public static JavaVersion getJavaVersion() {
		List<JavaVersion> candidates = Arrays.asList(JavaVersion.values());
		Collections.reverse(candidates);
		for (JavaVersion candidate : candidates) {
			if (candidate.available) {
				return candidate;
			}
		}
		return EIGHT;
	}

	/**
	 * Return if this version is equal to or newer than a given version.
	 * @param version the version to compare
	 * @return {@code true} if this version is equal to or newer than {@code version}
	 */
	public boolean isEqualOrNewerThan(JavaVersion version) {
		return compareTo(version) >= 0;
	}

	/**
	 * Return if this version is older than a given version.
	 * @param version the version to compare
	 * @return {@code true} if this version is older than {@code version}
	 */
	public boolean isOlderThan(JavaVersion version) {
		return compareTo(version) < 0;
	}

}
