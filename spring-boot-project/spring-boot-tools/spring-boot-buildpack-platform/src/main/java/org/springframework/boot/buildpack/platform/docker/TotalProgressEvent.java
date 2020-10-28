package org.springframework.boot.buildpack.platform.docker;

import org.springframework.util.Assert;

/**
 * Event published by the {@link TotalProgressPullListener} showing the total progress of
 * an operation.
 *

 * @since 2.3.0
 */
public class TotalProgressEvent {

	private final int percent;

	/**
	 * Create a new {@link TotalProgressEvent} with a specific percent value.
	 * @param percent the progress as a percentage
	 */
	public TotalProgressEvent(int percent) {
		Assert.isTrue(percent >= 0 && percent <= 100, "Percent must be in the range 0 to 100");
		this.percent = percent;
	}

	/**
	 * Return the total progress.
	 * @return the total progress
	 */
	public int getPercent() {
		return this.percent;
	}

}
