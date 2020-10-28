package org.springframework.boot.autoconfigure.rsocket;

import io.rsocket.transport.netty.server.TcpServerTransport;
import reactor.netty.http.server.HttpServer;

import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.messaging.rsocket.RSocketRequester;
import org.springframework.messaging.rsocket.RSocketStrategies;

/**
 * {@link EnableAutoConfiguration Auto-configuration} for
 * {@link org.springframework.messaging.rsocket.RSocketRequester}. This auto-configuration
 * creates {@link org.springframework.messaging.rsocket.RSocketRequester.Builder}
 * prototype beans, as the builders are stateful and should not be reused to build
 * requester instances with different configurations.
 *

 * @since 2.2.0
 */
@Configuration(proxyBeanMethods = false)
@ConditionalOnClass({ RSocketRequester.class, io.rsocket.RSocket.class, HttpServer.class, TcpServerTransport.class })
@AutoConfigureAfter(RSocketStrategiesAutoConfiguration.class)
public class RSocketRequesterAutoConfiguration {

	@Bean
	@Scope("prototype")
	@ConditionalOnMissingBean
	public RSocketRequester.Builder rSocketRequesterBuilder(RSocketStrategies strategies) {
		return RSocketRequester.builder().rsocketStrategies(strategies);
	}

}
