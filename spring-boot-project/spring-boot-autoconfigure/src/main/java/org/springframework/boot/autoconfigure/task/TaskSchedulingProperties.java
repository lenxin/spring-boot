package org.springframework.boot.autoconfigure.task;

import java.time.Duration;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * Configuration properties for task scheduling.
 *

 * @since 2.1.0
 */
@ConfigurationProperties("spring.task.scheduling")
public class TaskSchedulingProperties {

	private final Pool pool = new Pool();

	private final Shutdown shutdown = new Shutdown();

	/**
	 * Prefix to use for the names of newly created threads.
	 */
	private String threadNamePrefix = "scheduling-";

	public Pool getPool() {
		return this.pool;
	}

	public Shutdown getShutdown() {
		return this.shutdown;
	}

	public String getThreadNamePrefix() {
		return this.threadNamePrefix;
	}

	public void setThreadNamePrefix(String threadNamePrefix) {
		this.threadNamePrefix = threadNamePrefix;
	}

	public static class Pool {

		/**
		 * Maximum allowed number of threads.
		 */
		private int size = 1;

		public int getSize() {
			return this.size;
		}

		public void setSize(int size) {
			this.size = size;
		}

	}

	public static class Shutdown {

		/**
		 * Whether the executor should wait for scheduled tasks to complete on shutdown.
		 */
		private boolean awaitTermination;

		/**
		 * Maximum time the executor should wait for remaining tasks to complete.
		 */
		private Duration awaitTerminationPeriod;

		public boolean isAwaitTermination() {
			return this.awaitTermination;
		}

		public void setAwaitTermination(boolean awaitTermination) {
			this.awaitTermination = awaitTermination;
		}

		public Duration getAwaitTerminationPeriod() {
			return this.awaitTerminationPeriod;
		}

		public void setAwaitTerminationPeriod(Duration awaitTerminationPeriod) {
			this.awaitTerminationPeriod = awaitTerminationPeriod;
		}

	}

}
