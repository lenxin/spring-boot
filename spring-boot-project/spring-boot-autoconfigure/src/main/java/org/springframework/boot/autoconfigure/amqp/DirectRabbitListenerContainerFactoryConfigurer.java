package org.springframework.boot.autoconfigure.amqp;

import org.springframework.amqp.rabbit.config.DirectRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.boot.context.properties.PropertyMapper;

/**
 * Configure {@link DirectRabbitListenerContainerFactoryConfigurer} with sensible
 * defaults.
 *


 * @since 2.0.0
 */
public final class DirectRabbitListenerContainerFactoryConfigurer
		extends AbstractRabbitListenerContainerFactoryConfigurer<DirectRabbitListenerContainerFactory> {

	@Override
	public void configure(DirectRabbitListenerContainerFactory factory, ConnectionFactory connectionFactory) {
		PropertyMapper map = PropertyMapper.get();
		RabbitProperties.DirectContainer config = getRabbitProperties().getListener().getDirect();
		configure(factory, connectionFactory, config);
		map.from(config::getConsumersPerQueue).whenNonNull().to(factory::setConsumersPerQueue);
	}

}
