package io.spring.concourse.releasescripts.artifactory.payload;

/**
 * Represents a request to distribute artifacts from Artifactory to Bintray.
 *

 */
public class DistributionRequest {

	private final String[] sourceRepos;

	private final String targetRepo = "spring-distributions";

	private final String async = "true";

	public DistributionRequest(String[] sourceRepos) {
		this.sourceRepos = sourceRepos;
	}

	public String[] getSourceRepos() {
		return sourceRepos;
	}

	public String getTargetRepo() {
		return targetRepo;
	}

	public String getAsync() {
		return async;
	}

}
