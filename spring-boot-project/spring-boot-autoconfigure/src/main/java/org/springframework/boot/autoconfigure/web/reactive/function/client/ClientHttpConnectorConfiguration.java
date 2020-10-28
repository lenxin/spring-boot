package org.springframework.boot.autoconfigure.web.reactive.function.client;

import org.eclipse.jetty.client.HttpClient;
import org.eclipse.jetty.util.ssl.SslContextFactory;

import org.springframework.beans.factory.ObjectProvider;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.client.reactive.ClientHttpConnector;
import org.springframework.http.client.reactive.JettyClientHttpConnector;
import org.springframework.http.client.reactive.JettyResourceFactory;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.http.client.reactive.ReactorResourceFactory;

/**
 * Configuration classes for WebClient client connectors.
 * <p>
 * Those should be {@code @Import} in a regular auto-configuration class to guarantee
 * their order of execution.
 *

 */
@Configuration(proxyBeanMethods = false)
class ClientHttpConnectorConfiguration {

	@Configuration(proxyBeanMethods = false)
	@ConditionalOnClass(reactor.netty.http.client.HttpClient.class)
	@ConditionalOnMissingBean(ClientHttpConnector.class)
	public static class ReactorNetty {

		@Bean
		@ConditionalOnMissingBean
		public ReactorResourceFactory reactorClientResourceFactory() {
			return new ReactorResourceFactory();
		}

		@Bean
		@Lazy
		public ReactorClientHttpConnector reactorClientHttpConnector(ReactorResourceFactory reactorResourceFactory,
				ObjectProvider<ReactorNettyHttpClientMapper> mapperProvider) {
			ReactorNettyHttpClientMapper mapper = mapperProvider.orderedStream()
					.reduce((before, after) -> (client) -> after.configure(before.configure(client)))
					.orElse((client) -> client);
			return new ReactorClientHttpConnector(reactorResourceFactory, mapper::configure);
		}

	}

	@Configuration(proxyBeanMethods = false)
	@ConditionalOnClass(org.eclipse.jetty.reactive.client.ReactiveRequest.class)
	@ConditionalOnMissingBean(ClientHttpConnector.class)
	public static class JettyClient {

		@Bean
		@ConditionalOnMissingBean
		public JettyResourceFactory jettyClientResourceFactory() {
			return new JettyResourceFactory();
		}

		@Bean
		@Lazy
		public JettyClientHttpConnector jettyClientHttpConnector(JettyResourceFactory jettyResourceFactory) {
			SslContextFactory sslContextFactory = new SslContextFactory.Client();
			HttpClient httpClient = new HttpClient(sslContextFactory);
			return new JettyClientHttpConnector(httpClient, jettyResourceFactory);
		}

	}

}
