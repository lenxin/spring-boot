package org.springframework.boot.buildpack.platform.docker;

/**
 * A {@link ProgressUpdateEvent} fired for image events.
 *


 * @since 2.4.0
 */
public class ImageProgressUpdateEvent extends ProgressUpdateEvent {

	private final String id;

	protected ImageProgressUpdateEvent(String id, String status, ProgressDetail progressDetail, String progress) {
		super(status, progressDetail, progress);
		this.id = id;
	}

	/**
	 * Returns the ID of the image layer being updated if available.
	 * @return the ID of the updated layer or {@code null}
	 */
	public String getId() {
		return this.id;
	}

}
