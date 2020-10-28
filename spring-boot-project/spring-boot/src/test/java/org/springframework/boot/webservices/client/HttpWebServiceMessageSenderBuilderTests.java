package org.springframework.boot.webservices.client;

import java.time.Duration;

import org.apache.http.client.config.RequestConfig;
import org.junit.jupiter.api.Test;

import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.ws.transport.WebServiceMessageSender;
import org.springframework.ws.transport.http.ClientHttpRequestMessageSender;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;

/**
 * Tests for {@link HttpWebServiceMessageSenderBuilder}.
 *

 */
class HttpWebServiceMessageSenderBuilderTests {

	@Test
	void buildWithRequestFactorySupplier() {
		ClientHttpRequestFactory requestFactory = mock(ClientHttpRequestFactory.class);
		ClientHttpRequestMessageSender messageSender = build(
				new HttpWebServiceMessageSenderBuilder().requestFactory(() -> requestFactory));
		assertThat(messageSender.getRequestFactory()).isSameAs(requestFactory);
	}

	@Test
	void buildWithReadAndConnectTimeout() {
		ClientHttpRequestMessageSender messageSender = build(
				new HttpWebServiceMessageSenderBuilder().requestFactory(SimpleClientHttpRequestFactory::new)
						.setConnectTimeout(Duration.ofSeconds(5)).setReadTimeout(Duration.ofSeconds(2)));
		SimpleClientHttpRequestFactory requestFactory = (SimpleClientHttpRequestFactory) messageSender
				.getRequestFactory();
		assertThat(requestFactory).hasFieldOrPropertyWithValue("connectTimeout", 5000);
		assertThat(requestFactory).hasFieldOrPropertyWithValue("readTimeout", 2000);
	}

	@Test
	void buildUsesHttpComponentsByDefault() {
		ClientHttpRequestMessageSender messageSender = build(new HttpWebServiceMessageSenderBuilder()
				.setConnectTimeout(Duration.ofSeconds(5)).setReadTimeout(Duration.ofSeconds(2)));
		ClientHttpRequestFactory requestFactory = messageSender.getRequestFactory();
		assertThat(requestFactory).isInstanceOf(HttpComponentsClientHttpRequestFactory.class);
		RequestConfig requestConfig = (RequestConfig) ReflectionTestUtils.getField(requestFactory, "requestConfig");
		assertThat(requestConfig).isNotNull();
		assertThat(requestConfig.getConnectTimeout()).isEqualTo(5000);
		assertThat(requestConfig.getSocketTimeout()).isEqualTo(2000);
	}

	private ClientHttpRequestMessageSender build(HttpWebServiceMessageSenderBuilder builder) {
		WebServiceMessageSender messageSender = builder.build();
		assertThat(messageSender).isInstanceOf(ClientHttpRequestMessageSender.class);
		return ((ClientHttpRequestMessageSender) messageSender);
	}

}
