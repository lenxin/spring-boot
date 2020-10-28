package org.springframework.boot.actuate.endpoint;

/**
 * Indicate that an endpoint request is invalid.
 *

 * @since 2.0.0
 */
public class InvalidEndpointRequestException extends RuntimeException {

	private final String reason;

	public InvalidEndpointRequestException(String message, String reason) {
		super(message);
		this.reason = reason;
	}

	public InvalidEndpointRequestException(String message, String reason, Throwable cause) {
		super(message, cause);
		this.reason = reason;
	}

	/**
	 * Return the reason explaining why the request is invalid, potentially {@code null}.
	 * @return the reason for the failure
	 */
	public String getReason() {
		return this.reason;
	}

}
