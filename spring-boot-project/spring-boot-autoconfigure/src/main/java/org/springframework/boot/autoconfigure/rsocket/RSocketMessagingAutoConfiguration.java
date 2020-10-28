package org.springframework.boot.autoconfigure.rsocket;

import io.rsocket.transport.netty.server.TcpServerTransport;

import org.springframework.beans.factory.ObjectProvider;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.rsocket.RSocketRequester;
import org.springframework.messaging.rsocket.RSocketStrategies;
import org.springframework.messaging.rsocket.annotation.support.RSocketMessageHandler;

/**
 * {@link EnableAutoConfiguration Auto-configuration} for Spring RSocket support in Spring
 * Messaging.
 *

 * @since 2.2.0
 */
@Configuration(proxyBeanMethods = false)
@ConditionalOnClass({ RSocketRequester.class, io.rsocket.RSocket.class, TcpServerTransport.class })
@AutoConfigureAfter(RSocketStrategiesAutoConfiguration.class)
public class RSocketMessagingAutoConfiguration {

	@Bean
	@ConditionalOnMissingBean
	public RSocketMessageHandler messageHandler(RSocketStrategies rSocketStrategies,
			ObjectProvider<RSocketMessageHandlerCustomizer> customizers) {
		RSocketMessageHandler messageHandler = new RSocketMessageHandler();
		messageHandler.setRSocketStrategies(rSocketStrategies);
		customizers.orderedStream().forEach((customizer) -> customizer.customize(messageHandler));
		return messageHandler;
	}

}
