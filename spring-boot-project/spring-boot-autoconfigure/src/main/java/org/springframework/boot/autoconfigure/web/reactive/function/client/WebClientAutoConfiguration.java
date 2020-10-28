package org.springframework.boot.autoconfigure.web.reactive.function.client;

import java.util.stream.Collectors;

import org.springframework.beans.factory.ObjectProvider;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.http.codec.CodecsAutoConfiguration;
import org.springframework.boot.web.codec.CodecCustomizer;
import org.springframework.boot.web.reactive.function.client.WebClientCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.core.annotation.Order;
import org.springframework.web.reactive.function.client.WebClient;

/**
 * {@link EnableAutoConfiguration Auto-configuration} for {@link WebClient}.
 * <p>
 * This will produce a
 * {@link org.springframework.web.reactive.function.client.WebClient.Builder
 * WebClient.Builder} bean with the {@code prototype} scope, meaning each injection point
 * will receive a newly cloned instance of the builder.
 *

 * @since 2.0.0
 */
@Configuration(proxyBeanMethods = false)
@ConditionalOnClass(WebClient.class)
@AutoConfigureAfter({ CodecsAutoConfiguration.class, ClientHttpConnectorAutoConfiguration.class })
public class WebClientAutoConfiguration {

	@Bean
	@Scope("prototype")
	@ConditionalOnMissingBean
	public WebClient.Builder webClientBuilder(ObjectProvider<WebClientCustomizer> customizerProvider) {
		WebClient.Builder builder = WebClient.builder();
		customizerProvider.orderedStream().forEach((customizer) -> customizer.customize(builder));
		return builder;
	}

	@Configuration(proxyBeanMethods = false)
	@ConditionalOnBean(CodecCustomizer.class)
	protected static class WebClientCodecsConfiguration {

		@Bean
		@ConditionalOnMissingBean
		@Order(0)
		public WebClientCodecCustomizer exchangeStrategiesCustomizer(ObjectProvider<CodecCustomizer> codecCustomizers) {
			return new WebClientCodecCustomizer(codecCustomizers.orderedStream().collect(Collectors.toList()));
		}

	}

}
