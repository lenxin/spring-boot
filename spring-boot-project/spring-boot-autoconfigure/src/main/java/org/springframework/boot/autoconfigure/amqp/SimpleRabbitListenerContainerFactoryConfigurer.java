package org.springframework.boot.autoconfigure.amqp;

import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.boot.context.properties.PropertyMapper;

/**
 * Configure {@link SimpleRabbitListenerContainerFactoryConfigurer} with sensible
 * defaults.
 *


 * @since 1.3.3
 */
public final class SimpleRabbitListenerContainerFactoryConfigurer
		extends AbstractRabbitListenerContainerFactoryConfigurer<SimpleRabbitListenerContainerFactory> {

	@Override
	public void configure(SimpleRabbitListenerContainerFactory factory, ConnectionFactory connectionFactory) {
		PropertyMapper map = PropertyMapper.get();
		RabbitProperties.SimpleContainer config = getRabbitProperties().getListener().getSimple();
		configure(factory, connectionFactory, config);
		map.from(config::getConcurrency).whenNonNull().to(factory::setConcurrentConsumers);
		map.from(config::getMaxConcurrency).whenNonNull().to(factory::setMaxConcurrentConsumers);
		map.from(config::getBatchSize).whenNonNull().to(factory::setBatchSize);
		map.from(config::isConsumerBatchEnabled).to(factory::setConsumerBatchEnabled);
	}

}
