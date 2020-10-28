package io.spring.concourse.releasescripts;

/**
 * Release type.
 *

 */
public enum ReleaseType {

	MILESTONE("M", "libs-milestone-local"),

	RELEASE_CANDIDATE("RC", "libs-milestone-local"),

	RELEASE("RELEASE", "libs-release-local");

	private final String identifier;

	private final String repo;

	ReleaseType(String identifier, String repo) {
		this.identifier = identifier;
		this.repo = repo;
	}

	public static ReleaseType from(String releaseType) {
		for (ReleaseType type : ReleaseType.values()) {
			if (type.identifier.equals(releaseType)) {
				return type;
			}
		}
		throw new IllegalArgumentException("Invalid release type");
	}

	public String getRepo() {
		return this.repo;
	}

}
