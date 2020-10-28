package org.springframework.boot.buildpack.platform.docker;

import com.fasterxml.jackson.annotation.JsonCreator;

/**
 * A {@link ProgressUpdateEvent} fired as an image is loaded.
 *

 * @since 2.3.0
 */
public class LoadImageUpdateEvent extends ProgressUpdateEvent {

	private final String stream;

	@JsonCreator
	public LoadImageUpdateEvent(String stream, String status, ProgressDetail progressDetail, String progress) {
		super(status, progressDetail, progress);
		this.stream = stream;
	}

	/**
	 * Return the stream response or {@code null} if no response is available.
	 * @return the stream response.
	 */
	public String getStream() {
		return this.stream;
	}

}
