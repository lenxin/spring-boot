package org.springframework.boot.actuate.autoconfigure.cloudfoundry;

import org.springframework.http.HttpStatus;

/**
 * Response from the Cloud Foundry security interceptors.
 *

 * @since 2.0.0
 */
public class SecurityResponse {

	private final HttpStatus status;

	private final String message;

	public SecurityResponse(HttpStatus status) {
		this(status, null);
	}

	public SecurityResponse(HttpStatus status, String message) {
		this.status = status;
		this.message = message;
	}

	public HttpStatus getStatus() {
		return this.status;
	}

	public String getMessage() {
		return this.message;
	}

	public static SecurityResponse success() {
		return new SecurityResponse(HttpStatus.OK);
	}

}
