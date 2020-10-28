package org.springframework.boot.autoconfigure.web.servlet;

import org.junit.jupiter.api.Test;

import org.springframework.boot.autoconfigure.web.ServerProperties;
import org.springframework.boot.web.embedded.undertow.UndertowServletWebServerFactory;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Tests for {@link UndertowServletWebServerFactoryCustomizer}
 *

 */
class UndertowServletWebServerFactoryCustomizerTests {

	@Test
	void eagerFilterInitCanBeDisabled() {
		UndertowServletWebServerFactory factory = new UndertowServletWebServerFactory(0);
		assertThat(factory.isEagerFilterInit()).isTrue();
		ServerProperties serverProperties = new ServerProperties();
		serverProperties.getUndertow().setEagerFilterInit(false);
		new UndertowServletWebServerFactoryCustomizer(serverProperties).customize(factory);
		assertThat(factory.isEagerFilterInit()).isFalse();
	}

	@Test
	void preservePathOnForwardCanBeEnabled() {
		UndertowServletWebServerFactory factory = new UndertowServletWebServerFactory(0);
		assertThat(factory.isPreservePathOnForward()).isFalse();
		ServerProperties serverProperties = new ServerProperties();
		serverProperties.getUndertow().setPreservePathOnForward(true);
		new UndertowServletWebServerFactoryCustomizer(serverProperties).customize(factory);
		assertThat(factory.isPreservePathOnForward()).isTrue();
	}

}
