package org.springframework.boot.buildpack.platform.docker;

import com.fasterxml.jackson.annotation.JsonCreator;

/**
 * An {@link UpdateEvent} that includes progress information.
 *

 * @since 2.3.0
 */
public abstract class ProgressUpdateEvent extends UpdateEvent {

	private final String status;

	private final ProgressDetail progressDetail;

	private final String progress;

	protected ProgressUpdateEvent(String status, ProgressDetail progressDetail, String progress) {
		this.status = status;
		this.progressDetail = (ProgressDetail.isEmpty(progressDetail)) ? null : progressDetail;
		this.progress = progress;
	}

	/**
	 * Return the status for the update. For example, "Extracting" or "Downloading".
	 * @return the status of the update.
	 */
	public String getStatus() {
		return this.status;
	}

	/**
	 * Return progress details if available.
	 * @return progress details or {@code null}
	 */
	public ProgressDetail getProgressDetail() {
		return this.progressDetail;
	}

	/**
	 * Return a text based progress bar if progress information is available.
	 * @return the progress bar or {@code null}
	 */
	public String getProgress() {
		return this.progress;
	}

	/**
	 * Provide details about the progress of a task.
	 */
	public static class ProgressDetail {

		private final Integer current;

		private final Integer total;

		@JsonCreator
		public ProgressDetail(Integer current, Integer total) {
			this.current = current;
			this.total = total;
		}

		/**
		 * Return the current progress value.
		 * @return the current progress
		 */
		public int getCurrent() {
			return this.current;
		}

		/**
		 * Return the total progress possible value.
		 * @return the total progress possible
		 */
		public int getTotal() {
			return this.total;
		}

		public static boolean isEmpty(ProgressDetail progressDetail) {
			return progressDetail == null || progressDetail.current == null || progressDetail.total == null;
		}

	}

}
