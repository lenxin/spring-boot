package org.springframework.boot.task;

import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;

/**
 * Callback interface that can be used to customize a {@link ThreadPoolTaskScheduler}.
 *

 * @since 2.1.0
 */
@FunctionalInterface
public interface TaskSchedulerCustomizer {

	/**
	 * Callback to customize a {@link ThreadPoolTaskScheduler} instance.
	 * @param taskScheduler the task scheduler to customize
	 */
	void customize(ThreadPoolTaskScheduler taskScheduler);

}
