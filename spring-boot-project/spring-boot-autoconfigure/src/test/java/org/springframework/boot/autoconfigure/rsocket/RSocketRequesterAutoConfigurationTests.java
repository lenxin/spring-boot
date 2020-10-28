package org.springframework.boot.autoconfigure.rsocket;

import org.junit.jupiter.api.Test;

import org.springframework.boot.autoconfigure.AutoConfigurations;
import org.springframework.boot.test.context.runner.ApplicationContextRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.rsocket.RSocketRequester;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;

/**
 * Tests for {@link RSocketRequesterAutoConfiguration}
 *

 */
class RSocketRequesterAutoConfigurationTests {

	private final ApplicationContextRunner contextRunner = new ApplicationContextRunner().withConfiguration(
			AutoConfigurations.of(RSocketStrategiesAutoConfiguration.class, RSocketRequesterAutoConfiguration.class));

	@Test
	void shouldCreateBuilder() {
		this.contextRunner.run((context) -> assertThat(context).hasSingleBean(RSocketRequester.Builder.class));
	}

	@Test
	void shouldGetPrototypeScopedBean() {
		this.contextRunner.run((context) -> {
			RSocketRequester.Builder first = context.getBean(RSocketRequester.Builder.class);
			RSocketRequester.Builder second = context.getBean(RSocketRequester.Builder.class);
			assertThat(first).isNotEqualTo(second);
		});
	}

	@Test
	void shouldNotCreateBuilderIfAlreadyPresent() {
		this.contextRunner.withUserConfiguration(CustomRSocketRequesterBuilder.class).run((context) -> {
			RSocketRequester.Builder builder = context.getBean(RSocketRequester.Builder.class);
			assertThat(builder).isInstanceOf(MyRSocketRequesterBuilder.class);
		});
	}

	@Configuration(proxyBeanMethods = false)
	static class CustomRSocketRequesterBuilder {

		@Bean
		MyRSocketRequesterBuilder myRSocketRequesterBuilder() {
			return mock(MyRSocketRequesterBuilder.class);
		}

	}

	interface MyRSocketRequesterBuilder extends RSocketRequester.Builder {

	}

}
