package org.springframework.boot.gradle.plugin;

import org.gradle.api.Buildable;
import org.gradle.api.artifacts.PublishArtifact;
import org.gradle.api.artifacts.PublishArtifactSet;
import org.gradle.api.tasks.TaskDependency;

/**
 * A wrapper for a {@link PublishArtifactSet} that ensures that only a single artifact is
 * published, with a war file taking precedence over a jar file.
 *


 */
final class SinglePublishedArtifact implements Buildable {

	private final PublishArtifactSet artifacts;

	private PublishArtifact currentArtifact;

	SinglePublishedArtifact(PublishArtifactSet artifacts) {
		this.artifacts = artifacts;
	}

	void addCandidate(PublishArtifact candidate) {
		if (this.currentArtifact == null || "war".equals(candidate.getExtension())) {
			this.artifacts.remove(this.currentArtifact);
			this.artifacts.add(candidate);
			this.currentArtifact = candidate;
		}
	}

	@Override
	public TaskDependency getBuildDependencies() {
		return this.artifacts.getBuildDependencies();
	}

}
