package org.springframework.boot.test.autoconfigure.web.reactive;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.ObjectProvider;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.http.codec.CodecsAutoConfiguration;
import org.springframework.boot.autoconfigure.web.reactive.WebFluxAutoConfiguration;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.test.web.reactive.server.WebTestClientBuilderCustomizer;
import org.springframework.boot.web.codec.CodecCustomizer;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.test.web.reactive.server.MockServerConfigurer;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.server.WebHandler;

/**
 * Auto-configuration for {@link WebTestClient}.
 *


 * @since 2.0.0
 */
@Configuration(proxyBeanMethods = false)
@ConditionalOnClass({ WebClient.class, WebTestClient.class })
@AutoConfigureAfter({ CodecsAutoConfiguration.class, WebFluxAutoConfiguration.class })
@Import(WebTestClientSecurityConfiguration.class)
@EnableConfigurationProperties
public class WebTestClientAutoConfiguration {

	@Bean
	@ConditionalOnMissingBean
	@ConditionalOnBean(WebHandler.class)
	public WebTestClient webTestClient(ApplicationContext applicationContext,
			List<WebTestClientBuilderCustomizer> customizers, List<MockServerConfigurer> configurers) {
		WebTestClient.MockServerSpec<?> mockServerSpec = WebTestClient.bindToApplicationContext(applicationContext);
		for (MockServerConfigurer configurer : configurers) {
			mockServerSpec.apply(configurer);
		}
		WebTestClient.Builder builder = mockServerSpec.configureClient();
		for (WebTestClientBuilderCustomizer customizer : customizers) {
			customizer.customize(builder);
		}
		return builder.build();
	}

	@Bean
	@ConfigurationProperties(prefix = "spring.test.webtestclient")
	public SpringBootWebTestClientBuilderCustomizer springBootWebTestClientBuilderCustomizer(
			ObjectProvider<CodecCustomizer> codecCustomizers) {
		return new SpringBootWebTestClientBuilderCustomizer(
				codecCustomizers.orderedStream().collect(Collectors.toList()));
	}

}
