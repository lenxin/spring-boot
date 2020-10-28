package org.springframework.boot.autoconfigure.rsocket;

import org.junit.jupiter.api.Test;

import org.springframework.boot.autoconfigure.AutoConfigurations;
import org.springframework.boot.test.context.runner.ApplicationContextRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.codec.CharSequenceEncoder;
import org.springframework.core.codec.StringDecoder;
import org.springframework.messaging.rsocket.RSocketStrategies;
import org.springframework.messaging.rsocket.annotation.support.RSocketMessageHandler;
import org.springframework.util.MimeType;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Tests for {@link RSocketMessagingAutoConfiguration}.
 *


 */
class RSocketMessagingAutoConfigurationTests {

	private final ApplicationContextRunner contextRunner = new ApplicationContextRunner()
			.withConfiguration(AutoConfigurations.of(RSocketMessagingAutoConfiguration.class))
			.withUserConfiguration(BaseConfiguration.class);

	@Test
	void shouldCreateDefaultBeans() {
		this.contextRunner.run((context) -> assertThat(context).getBeans(RSocketMessageHandler.class).hasSize(1));
	}

	@Test
	void shouldFailOnMissingStrategies() {
		new ApplicationContextRunner().withConfiguration(AutoConfigurations.of(RSocketMessagingAutoConfiguration.class))
				.run((context) -> {
					assertThat(context).hasFailed();
					assertThat(context.getStartupFailure().getMessage()).contains("No qualifying bean of type "
							+ "'org.springframework.messaging.rsocket.RSocketStrategies' available");
				});
	}

	@Test
	void shouldUseCustomSocketAcceptor() {
		this.contextRunner.withUserConfiguration(CustomMessageHandler.class).run((context) -> assertThat(context)
				.getBeanNames(RSocketMessageHandler.class).containsOnly("customMessageHandler"));
	}

	@Test
	void shouldApplyMessageHandlerCustomizers() {
		this.contextRunner.withUserConfiguration(CustomizerConfiguration.class).run((context) -> {
			RSocketMessageHandler handler = context.getBean(RSocketMessageHandler.class);
			assertThat(handler.getDefaultDataMimeType()).isEqualTo(MimeType.valueOf("application/json"));
		});
	}

	@Configuration(proxyBeanMethods = false)
	static class BaseConfiguration {

		@Bean
		RSocketStrategies rSocketStrategies() {
			return RSocketStrategies.builder().encoder(CharSequenceEncoder.textPlainOnly())
					.decoder(StringDecoder.allMimeTypes()).build();
		}

	}

	@Configuration(proxyBeanMethods = false)
	static class CustomMessageHandler {

		@Bean
		RSocketMessageHandler customMessageHandler() {
			RSocketMessageHandler messageHandler = new RSocketMessageHandler();
			RSocketStrategies strategies = RSocketStrategies.builder().encoder(CharSequenceEncoder.textPlainOnly())
					.decoder(StringDecoder.allMimeTypes()).build();
			messageHandler.setRSocketStrategies(strategies);
			return messageHandler;
		}

	}

	@Configuration(proxyBeanMethods = false)
	static class CustomizerConfiguration {

		@Bean
		RSocketMessageHandlerCustomizer customizer() {
			return (messageHandler) -> messageHandler.setDefaultDataMimeType(MimeType.valueOf("application/json"));
		}

	}

}
