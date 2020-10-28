package org.springframework.boot.autoconfigure.kafka;

import org.springframework.kafka.core.DefaultKafkaConsumerFactory;

/**
 * Callback interface for customizing {@code DefaultKafkaConsumerFactory} beans.
 *

 * @since 2.3.0
 */
@FunctionalInterface
public interface DefaultKafkaConsumerFactoryCustomizer {

	/**
	 * Customize the {@link DefaultKafkaConsumerFactory}.
	 * @param consumerFactory the consumer factory to customize
	 */
	void customize(DefaultKafkaConsumerFactory<?, ?> consumerFactory);

}
