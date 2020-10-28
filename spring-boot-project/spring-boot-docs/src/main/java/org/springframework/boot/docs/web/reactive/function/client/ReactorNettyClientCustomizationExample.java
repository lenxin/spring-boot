package org.springframework.boot.docs.web.reactive.function.client;

import io.netty.channel.ChannelOption;
import io.netty.handler.timeout.ReadTimeoutHandler;
import reactor.netty.http.client.HttpClient;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.reactive.ClientHttpConnector;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.http.client.reactive.ReactorResourceFactory;
import org.springframework.web.reactive.function.client.WebClient;

/**
 * Example configuration for customizing the Reactor Netty-based {@link WebClient}.
 *

 */
@Configuration(proxyBeanMethods = false)
public class ReactorNettyClientCustomizationExample {

	// tag::custom-http-connector[]
	@Bean
	ClientHttpConnector clientHttpConnector(ReactorResourceFactory resourceFactory) {
		HttpClient httpClient = HttpClient.create(resourceFactory.getConnectionProvider())
				.runOn(resourceFactory.getLoopResources()).option(ChannelOption.CONNECT_TIMEOUT_MILLIS, 60000)
				.doOnConnected((connection) -> connection.addHandlerLast(new ReadTimeoutHandler(60)));
		return new ReactorClientHttpConnector(httpClient);
	}
	// end::custom-http-connector[]

}
