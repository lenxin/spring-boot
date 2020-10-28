package org.springframework.boot.loader.tools;

/**
 * Encapsulates information about the artifact coordinates of a library.
 *

 */
class DefaultLibraryCoordinates implements LibraryCoordinates {

	private final String groupId;

	private final String artifactId;

	private final String version;

	/**
	 * Create a new instance from discrete elements.
	 * @param groupId the group ID
	 * @param artifactId the artifact ID
	 * @param version the version
	 */
	DefaultLibraryCoordinates(String groupId, String artifactId, String version) {
		this.groupId = groupId;
		this.artifactId = artifactId;
		this.version = version;
	}

	/**
	 * Return the group ID of the coordinates.
	 * @return the group ID
	 */
	@Override
	public String getGroupId() {
		return this.groupId;
	}

	/**
	 * Return the artifact ID of the coordinates.
	 * @return the artifact ID
	 */
	@Override
	public String getArtifactId() {
		return this.artifactId;
	}

	/**
	 * Return the version of the coordinates.
	 * @return the version
	 */
	@Override
	public String getVersion() {
		return this.version;
	}

	/**
	 * Return the coordinates in the form {@code groupId:artifactId:version}.
	 */
	@Override
	public String toString() {
		return LibraryCoordinates.toStandardNotationString(this);
	}

}
