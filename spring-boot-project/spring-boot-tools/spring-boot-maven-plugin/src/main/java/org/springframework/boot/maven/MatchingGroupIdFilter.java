package org.springframework.boot.maven;

import org.apache.maven.artifact.Artifact;
import org.apache.maven.shared.artifact.filter.collection.AbstractArtifactFeatureFilter;

/**
 * An {@link org.apache.maven.shared.artifact.filter.collection.ArtifactsFilter
 * ArtifactsFilter} that filters by matching groupId.
 *
 * Preferred over the
 * {@link org.apache.maven.shared.artifact.filter.collection.GroupIdFilter} due to that
 * classes use of {@link String#startsWith} to match on prefix.
 *

 * @since 1.1.0
 */
public class MatchingGroupIdFilter extends AbstractArtifactFeatureFilter {

	/**
	 * Create a new instance with the CSV groupId values that should be excluded.
	 * @param exclude the group values to exclude
	 */
	public MatchingGroupIdFilter(String exclude) {
		super("", exclude);
	}

	@Override
	protected String getArtifactFeature(Artifact artifact) {
		return artifact.getGroupId();
	}

}
