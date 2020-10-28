package org.springframework.boot.autoconfigure.web.reactive.function.client;

import java.util.concurrent.Executor;

import org.eclipse.jetty.client.HttpClient;
import org.eclipse.jetty.io.ByteBufferPool;
import org.eclipse.jetty.util.thread.Scheduler;
import org.junit.jupiter.api.Test;

import org.springframework.boot.autoconfigure.AutoConfigurations;
import org.springframework.boot.test.context.runner.ReactiveWebApplicationContextRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.http.client.reactive.JettyClientHttpConnector;
import org.springframework.http.client.reactive.JettyResourceFactory;
import org.springframework.test.util.ReflectionTestUtils;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;

/**
 * Tests for {@link ClientHttpConnectorConfiguration}.
 *


 */
class ClientHttpConnectorConfigurationTests {

	@Test
	void jettyClientHttpConnectorAppliesJettyResourceFactory() {
		Executor executor = mock(Executor.class);
		ByteBufferPool byteBufferPool = mock(ByteBufferPool.class);
		Scheduler scheduler = mock(Scheduler.class);
		JettyResourceFactory jettyResourceFactory = new JettyResourceFactory();
		jettyResourceFactory.setExecutor(executor);
		jettyResourceFactory.setByteBufferPool(byteBufferPool);
		jettyResourceFactory.setScheduler(scheduler);
		JettyClientHttpConnector connector = getClientHttpConnector(jettyResourceFactory);
		HttpClient httpClient = (HttpClient) ReflectionTestUtils.getField(connector, "httpClient");
		assertThat(httpClient.getExecutor()).isSameAs(executor);
		assertThat(httpClient.getByteBufferPool()).isSameAs(byteBufferPool);
		assertThat(httpClient.getScheduler()).isSameAs(scheduler);
	}

	@Test
	void JettyResourceFactoryHasSslContextFactory() {
		// gh-16810
		JettyResourceFactory jettyResourceFactory = new JettyResourceFactory();
		JettyClientHttpConnector connector = getClientHttpConnector(jettyResourceFactory);
		HttpClient httpClient = (HttpClient) ReflectionTestUtils.getField(connector, "httpClient");
		assertThat(httpClient.getSslContextFactory()).isNotNull();
	}

	private JettyClientHttpConnector getClientHttpConnector(JettyResourceFactory jettyResourceFactory) {
		ClientHttpConnectorConfiguration.JettyClient jettyClient = new ClientHttpConnectorConfiguration.JettyClient();
		// We shouldn't usually call this method directly since it's on a non-proxy config
		return ReflectionTestUtils.invokeMethod(jettyClient, "jettyClientHttpConnector", jettyResourceFactory);
	}

	@Test
	void shouldApplyHttpClientMapper() {
		new ReactiveWebApplicationContextRunner()
				.withConfiguration(AutoConfigurations.of(ClientHttpConnectorConfiguration.ReactorNetty.class))
				.withUserConfiguration(CustomHttpClientMapper.class).run((context) -> {
					context.getBean("reactorClientHttpConnector");
					assertThat(CustomHttpClientMapper.called).isTrue();
				});
	}

	static class CustomHttpClientMapper {

		static boolean called = false;

		@Bean
		ReactorNettyHttpClientMapper clientMapper() {
			return (client) -> {
				called = true;
				return client.baseUrl("/test");
			};
		}

	}

}
