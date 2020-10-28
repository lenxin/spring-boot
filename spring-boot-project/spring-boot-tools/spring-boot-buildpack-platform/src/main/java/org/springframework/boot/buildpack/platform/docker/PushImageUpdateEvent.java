package org.springframework.boot.buildpack.platform.docker;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * A {@link ProgressUpdateEvent} fired as an image is pushed to a registry.
 *

 * @since 2.4.0
 */
public class PushImageUpdateEvent extends ImageProgressUpdateEvent {

	private final ErrorDetail errorDetail;

	@JsonCreator
	public PushImageUpdateEvent(String id, String status, ProgressDetail progressDetail, String progress,
			ErrorDetail errorDetail) {
		super(id, status, progressDetail, progress);
		this.errorDetail = errorDetail;
	}

	/**
	 * Returns the details of any error encountered during processing.
	 * @return the error
	 */
	public ErrorDetail getErrorDetail() {
		return this.errorDetail;
	}

	/**
	 * Details of an error embedded in a response stream.
	 */
	public static class ErrorDetail {

		private final String message;

		@JsonCreator
		public ErrorDetail(@JsonProperty("message") String message) {
			this.message = message;
		}

		/**
		 * Returns the message field from the error detail.
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

}
