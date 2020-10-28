package io.spring.concourse.releasescripts.artifactory;

/**
 * Runtime exception if artifact distribution to Bintray fails.
 *

 */
public class DistributionTimeoutException extends RuntimeException {

	private String message;

	DistributionTimeoutException(String message) {
		super(message);
		this.message = message;
	}

	@Override
	public String getMessage() {
		return this.message;
	}

}
