package org.springframework.boot.autoconfigure.amqp;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.listener.AbstractMessageListenerContainer;
import org.springframework.retry.support.RetryTemplate;

/**
 * Callback interface that can be used to customize a {@link RetryTemplate} used as part
 * of the Rabbit infrastructure.
 *

 * @since 2.1.0
 */
@FunctionalInterface
public interface RabbitRetryTemplateCustomizer {

	/**
	 * Callback to customize a {@link RetryTemplate} instance used in the context of the
	 * specified {@link Target}.
	 * @param target the {@link Target} of the retry template
	 * @param retryTemplate the template to customize
	 */
	void customize(Target target, RetryTemplate retryTemplate);

	/**
	 * Define the available target for a {@link RetryTemplate}.
	 */
	enum Target {

		/**
		 * {@link RabbitTemplate} target.
		 */
		SENDER,

		/**
		 * {@link AbstractMessageListenerContainer} target.
		 */
		LISTENER

	}

}
