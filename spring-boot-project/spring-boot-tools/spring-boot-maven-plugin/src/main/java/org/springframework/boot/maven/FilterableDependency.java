package org.springframework.boot.maven;

import org.apache.maven.plugins.annotations.Parameter;

/**
 * A model for a dependency to include or exclude.
 *


 */
abstract class FilterableDependency {

	/**
	 * The groupId of the artifact to exclude.
	 */
	@Parameter(required = true)
	private String groupId;

	/**
	 * The artifactId of the artifact to exclude.
	 */
	@Parameter(required = true)
	private String artifactId;

	/**
	 * The classifier of the artifact to exclude.
	 */
	@Parameter
	private String classifier;

	String getGroupId() {
		return this.groupId;
	}

	void setGroupId(String groupId) {
		this.groupId = groupId;
	}

	String getArtifactId() {
		return this.artifactId;
	}

	void setArtifactId(String artifactId) {
		this.artifactId = artifactId;
	}

	String getClassifier() {
		return this.classifier;
	}

	void setClassifier(String classifier) {
		this.classifier = classifier;
	}

}
