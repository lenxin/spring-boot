package org.springframework.boot.autoconfigure.web.reactive;

import java.net.InetAddress;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import org.springframework.boot.autoconfigure.web.ServerProperties;
import org.springframework.boot.web.reactive.server.ConfigurableReactiveWebServerFactory;
import org.springframework.boot.web.server.Shutdown;
import org.springframework.boot.web.server.Ssl;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

/**
 * Tests for {@link ReactiveWebServerFactoryCustomizer}.
 *


 */
class ReactiveWebServerFactoryCustomizerTests {

	private ServerProperties properties = new ServerProperties();

	private ReactiveWebServerFactoryCustomizer customizer;

	@BeforeEach
	void setup() {
		this.customizer = new ReactiveWebServerFactoryCustomizer(this.properties);
	}

	@Test
	void testCustomizeServerPort() {
		ConfigurableReactiveWebServerFactory factory = mock(ConfigurableReactiveWebServerFactory.class);
		this.properties.setPort(9000);
		this.customizer.customize(factory);
		verify(factory).setPort(9000);
	}

	@Test
	void testCustomizeServerAddress() {
		ConfigurableReactiveWebServerFactory factory = mock(ConfigurableReactiveWebServerFactory.class);
		InetAddress address = mock(InetAddress.class);
		this.properties.setAddress(address);
		this.customizer.customize(factory);
		verify(factory).setAddress(address);
	}

	@Test
	void testCustomizeServerSsl() {
		ConfigurableReactiveWebServerFactory factory = mock(ConfigurableReactiveWebServerFactory.class);
		Ssl ssl = mock(Ssl.class);
		this.properties.setSsl(ssl);
		this.customizer.customize(factory);
		verify(factory).setSsl(ssl);
	}

	@Test
	void whenShutdownPropertyIsSetThenShutdownIsCustomized() {
		this.properties.setShutdown(Shutdown.GRACEFUL);
		ConfigurableReactiveWebServerFactory factory = mock(ConfigurableReactiveWebServerFactory.class);
		this.customizer.customize(factory);
		ArgumentCaptor<Shutdown> shutdownCaptor = ArgumentCaptor.forClass(Shutdown.class);
		verify(factory).setShutdown(shutdownCaptor.capture());
		assertThat(shutdownCaptor.getValue()).isEqualTo(Shutdown.GRACEFUL);
	}

}
