package org.springframework.boot.autoconfigure.kafka;

import org.springframework.kafka.core.DefaultKafkaProducerFactory;

/**
 * Callback interface for customizing {@code DefaultKafkaProducerFactory} beans.
 *

 * @since 2.3.0
 */
@FunctionalInterface
public interface DefaultKafkaProducerFactoryCustomizer {

	/**
	 * Customize the {@link DefaultKafkaProducerFactory}.
	 * @param producerFactory the producer factory to customize
	 */
	void customize(DefaultKafkaProducerFactory<?, ?> producerFactory);

}
