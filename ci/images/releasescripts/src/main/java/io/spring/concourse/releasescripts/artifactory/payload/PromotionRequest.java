package io.spring.concourse.releasescripts.artifactory.payload;

/**
 * Represents a request to promote artifacts from a sourceRepo to a targetRepo.
 *

 */
public class PromotionRequest {

	private final String status;

	private final String sourceRepo;

	private final String targetRepo;

	public PromotionRequest(String status, String sourceRepo, String targetRepo) {
		this.status = status;
		this.sourceRepo = sourceRepo;
		this.targetRepo = targetRepo;
	}

	public String getTargetRepo() {
		return this.targetRepo;
	}

	public String getSourceRepo() {
		return this.sourceRepo;
	}

	public String getStatus() {
		return this.status;
	}

}
