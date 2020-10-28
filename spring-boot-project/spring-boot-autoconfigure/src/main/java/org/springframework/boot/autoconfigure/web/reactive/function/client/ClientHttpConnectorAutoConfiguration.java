package org.springframework.boot.autoconfigure.web.reactive.function.client;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.web.reactive.function.client.WebClientCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.Lazy;
import org.springframework.core.annotation.Order;
import org.springframework.http.client.reactive.ClientHttpConnector;
import org.springframework.web.reactive.function.client.WebClient;

/**
 * {@link EnableAutoConfiguration Auto-configuration} for {@link ClientHttpConnector}.
 * <p>
 * It can produce a {@link org.springframework.http.client.reactive.ClientHttpConnector}
 * bean and possibly a companion {@code ResourceFactory} bean, depending on the chosen
 * HTTP client library.
 *

 * @since 2.1.0
 */
@Configuration(proxyBeanMethods = false)
@ConditionalOnClass(WebClient.class)
@Import({ ClientHttpConnectorConfiguration.ReactorNetty.class, ClientHttpConnectorConfiguration.JettyClient.class })
public class ClientHttpConnectorAutoConfiguration {

	@Bean
	@Lazy
	@Order(0)
	@ConditionalOnBean(ClientHttpConnector.class)
	public WebClientCustomizer clientConnectorCustomizer(ClientHttpConnector clientHttpConnector) {
		return (builder) -> builder.clientConnector(clientHttpConnector);
	}

}
