package org.springframework.boot.autoconfigure.data.elasticsearch;

import reactor.netty.http.client.HttpClient;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.context.properties.PropertyMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.elasticsearch.client.ClientConfiguration;
import org.springframework.data.elasticsearch.client.reactive.ReactiveElasticsearchClient;
import org.springframework.data.elasticsearch.client.reactive.ReactiveRestClients;
import org.springframework.http.HttpHeaders;
import org.springframework.util.unit.DataSize;
import org.springframework.web.reactive.function.client.ExchangeStrategies;
import org.springframework.web.reactive.function.client.WebClient;

/**
 * {@link EnableAutoConfiguration Auto-configuration} for Elasticsearch Reactive REST
 * clients.
 *

 * @since 2.2.0
 */
@Configuration(proxyBeanMethods = false)
@ConditionalOnClass({ ReactiveRestClients.class, WebClient.class, HttpClient.class })
@EnableConfigurationProperties(ReactiveElasticsearchRestClientProperties.class)
public class ReactiveElasticsearchRestClientAutoConfiguration {

	@Bean
	@ConditionalOnMissingBean
	public ClientConfiguration clientConfiguration(ReactiveElasticsearchRestClientProperties properties) {
		ClientConfiguration.MaybeSecureClientConfigurationBuilder builder = ClientConfiguration.builder()
				.connectedTo(properties.getEndpoints().toArray(new String[0]));
		if (properties.isUseSsl()) {
			builder.usingSsl();
		}
		configureTimeouts(builder, properties);
		configureExchangeStrategies(builder, properties);
		return builder.build();
	}

	private void configureTimeouts(ClientConfiguration.TerminalClientConfigurationBuilder builder,
			ReactiveElasticsearchRestClientProperties properties) {
		PropertyMapper map = PropertyMapper.get();
		map.from(properties.getConnectionTimeout()).whenNonNull().to(builder::withConnectTimeout);
		map.from(properties.getSocketTimeout()).whenNonNull().to(builder::withSocketTimeout);
		map.from(properties.getUsername()).whenHasText().to((username) -> {
			HttpHeaders headers = new HttpHeaders();
			headers.setBasicAuth(username, properties.getPassword());
			builder.withDefaultHeaders(headers);
		});
	}

	private void configureExchangeStrategies(ClientConfiguration.TerminalClientConfigurationBuilder builder,
			ReactiveElasticsearchRestClientProperties properties) {
		PropertyMapper map = PropertyMapper.get();
		builder.withWebClientConfigurer((webClient) -> {
			ExchangeStrategies exchangeStrategies = ExchangeStrategies.builder()
					.codecs((configurer) -> map.from(properties.getMaxInMemorySize()).whenNonNull()
							.asInt(DataSize::toBytes)
							.to((maxInMemorySize) -> configurer.defaultCodecs().maxInMemorySize(maxInMemorySize)))
					.build();
			return webClient.mutate().exchangeStrategies(exchangeStrategies).build();
		});
	}

	@Bean
	@ConditionalOnMissingBean
	public ReactiveElasticsearchClient reactiveElasticsearchClient(ClientConfiguration clientConfiguration) {
		return ReactiveRestClients.create(clientConfiguration);
	}

}
