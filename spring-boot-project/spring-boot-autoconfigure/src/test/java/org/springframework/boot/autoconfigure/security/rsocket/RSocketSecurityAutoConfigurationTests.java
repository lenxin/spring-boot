package org.springframework.boot.autoconfigure.security.rsocket;

import io.rsocket.core.RSocketServer;
import org.junit.jupiter.api.Test;

import org.springframework.boot.autoconfigure.AutoConfigurations;
import org.springframework.boot.autoconfigure.rsocket.RSocketMessagingAutoConfiguration;
import org.springframework.boot.autoconfigure.rsocket.RSocketStrategiesAutoConfiguration;
import org.springframework.boot.autoconfigure.security.reactive.ReactiveUserDetailsServiceAutoConfiguration;
import org.springframework.boot.rsocket.server.RSocketServerCustomizer;
import org.springframework.boot.test.context.FilteredClassLoader;
import org.springframework.boot.test.context.runner.ApplicationContextRunner;
import org.springframework.security.config.annotation.rsocket.RSocketSecurity;
import org.springframework.security.rsocket.core.SecuritySocketAcceptorInterceptor;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Tests for {@link RSocketSecurityAutoConfiguration}.
 *


 */
class RSocketSecurityAutoConfigurationTests {

	private final ApplicationContextRunner contextRunner = new ApplicationContextRunner()
			.withConfiguration(AutoConfigurations.of(ReactiveUserDetailsServiceAutoConfiguration.class,
					RSocketSecurityAutoConfiguration.class, RSocketMessagingAutoConfiguration.class,
					RSocketStrategiesAutoConfiguration.class));

	@Test
	void autoConfigurationEnablesRSocketSecurity() {
		this.contextRunner.run((context) -> assertThat(context.getBean(RSocketSecurity.class)).isNotNull());
	}

	@Test
	void autoConfigurationIsConditionalOnSecuritySocketAcceptorInterceptorClass() {
		this.contextRunner.withClassLoader(new FilteredClassLoader(SecuritySocketAcceptorInterceptor.class))
				.run((context) -> assertThat(context).doesNotHaveBean(RSocketSecurity.class));
	}

	@Test
	void autoConfigurationAddsCustomizerForServerRSocketFactory() {
		RSocketServer server = RSocketServer.create();
		this.contextRunner.run((context) -> {
			RSocketServerCustomizer customizer = context.getBean(RSocketServerCustomizer.class);
			customizer.customize(server);
			server.interceptors((registry) -> registry.forSocketAcceptor((interceptors) -> {
				assertThat(interceptors).isNotEmpty();
				assertThat(interceptors)
						.anyMatch((interceptor) -> interceptor instanceof SecuritySocketAcceptorInterceptor);
			}));
		});
	}

}
