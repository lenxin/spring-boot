package org.springframework.boot.buildpack.platform.docker.type;

import java.io.IOException;
import java.io.InputStream;
import java.lang.invoke.MethodHandles;

import com.fasterxml.jackson.databind.JsonNode;

import org.springframework.boot.buildpack.platform.json.MappedObject;

/**
 * Status details returned from {@code Docker container wait}.
 *

 * @since 2.3.0
 */
public class ContainerStatus extends MappedObject {

	private final int statusCode;

	private final String waitingErrorMessage;

	ContainerStatus(int statusCode, String waitingErrorMessage) {
		super(null, null);
		this.statusCode = statusCode;
		this.waitingErrorMessage = waitingErrorMessage;
	}

	ContainerStatus(JsonNode node) {
		super(node, MethodHandles.lookup());
		this.statusCode = valueAt("/StatusCode", Integer.class);
		this.waitingErrorMessage = valueAt("/Error/Message", String.class);
	}

	/**
	 * Return the container exit status code.
	 * @return the exit status code
	 */
	public int getStatusCode() {
		return this.statusCode;
	}

	/**
	 * Return a message indicating an error waiting for a container to stop.
	 * @return the waiting error message
	 */
	public String getWaitingErrorMessage() {
		return this.waitingErrorMessage;
	}

	/**
	 * Create a new {@link ContainerStatus} instance from the specified JSON content
	 * stream.
	 * @param content the JSON content stream
	 * @return a new {@link ContainerStatus} instance
	 * @throws IOException on IO error
	 */
	public static ContainerStatus of(InputStream content) throws IOException {
		return of(content, ContainerStatus::new);
	}

	/**
	 * Create a new {@link ContainerStatus} instance with the specified values.
	 * @param statusCode the status code
	 * @param errorMessage the error message
	 * @return a new {@link ContainerStatus} instance
	 */
	public static ContainerStatus of(int statusCode, String errorMessage) {
		return new ContainerStatus(statusCode, errorMessage);
	}

}
