package org.springframework.boot.loader.tools;

/**
 * Encapsulates information about the artifact coordinates of a library.
 *


 * @since 2.3.0
 */
public interface LibraryCoordinates {

	/**
	 * Return the group ID of the coordinates.
	 * @return the group ID
	 */
	String getGroupId();

	/**
	 * Return the artifact ID of the coordinates.
	 * @return the artifact ID
	 */
	String getArtifactId();

	/**
	 * Return the version of the coordinates.
	 * @return the version
	 */
	String getVersion();

	/**
	 * Factory method to create {@link LibraryCoordinates} with the specified values.
	 * @param groupId the group ID
	 * @param artifactId the artifact ID
	 * @param version the version
	 * @return a new {@link LibraryCoordinates} instance
	 */
	static LibraryCoordinates of(String groupId, String artifactId, String version) {
		return new DefaultLibraryCoordinates(groupId, artifactId, version);
	}

	/**
	 * Utility method that returns the given coordinates using the standard
	 * {@code group:artifact:version} form.
	 * @param coordinates the coordinates to convert (may be {@code null})
	 * @return the standard notation form or {@code "::"} when the coordinates are null
	 */
	static String toStandardNotationString(LibraryCoordinates coordinates) {
		if (coordinates == null) {
			return "::";
		}
		StringBuilder builder = new StringBuilder();
		builder.append((coordinates.getGroupId() != null) ? coordinates.getGroupId() : "");
		builder.append(":");
		builder.append((coordinates.getArtifactId() != null) ? coordinates.getArtifactId() : "");
		builder.append(":");
		builder.append((coordinates.getVersion() != null) ? coordinates.getVersion() : "");
		return builder.toString();
	}

}
