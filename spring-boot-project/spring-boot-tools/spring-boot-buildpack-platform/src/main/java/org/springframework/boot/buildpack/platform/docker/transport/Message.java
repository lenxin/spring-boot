package org.springframework.boot.buildpack.platform.docker.transport;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * A message returned from the Docker API.
 *

 * @since 2.3.1
 */
public class Message {

	private final String message;

	@JsonCreator
	Message(@JsonProperty("message") String message) {
		this.message = message;
	}

	/**
	 * Return the message contained in the response.
	 * @return the message
	 */
	public String getMessage() {
		return this.message;
	}

	@Override
	public String toString() {
		return this.message;
	}

}
