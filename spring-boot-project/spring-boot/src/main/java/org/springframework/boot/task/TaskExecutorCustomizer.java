package org.springframework.boot.task;

import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

/**
 * Callback interface that can be used to customize a {@link ThreadPoolTaskExecutor}.
 *

 * @since 2.1.0
 * @see TaskExecutorBuilder
 */
@FunctionalInterface
public interface TaskExecutorCustomizer {

	/**
	 * Callback to customize a {@link ThreadPoolTaskExecutor} instance.
	 * @param taskExecutor the task executor to customize
	 */
	void customize(ThreadPoolTaskExecutor taskExecutor);

}
