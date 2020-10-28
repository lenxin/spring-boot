package org.springframework.boot.autoconfigure.web.servlet;

import org.springframework.boot.autoconfigure.web.ServerProperties;
import org.springframework.boot.web.embedded.undertow.UndertowServletWebServerFactory;
import org.springframework.boot.web.server.WebServerFactoryCustomizer;

/**
 * {@link WebServerFactoryCustomizer} to apply {@link ServerProperties} to Undertow
 * Servlet web servers.
 *

 * @since 2.1.7
 */
public class UndertowServletWebServerFactoryCustomizer
		implements WebServerFactoryCustomizer<UndertowServletWebServerFactory> {

	private final ServerProperties serverProperties;

	public UndertowServletWebServerFactoryCustomizer(ServerProperties serverProperties) {
		this.serverProperties = serverProperties;
	}

	@Override
	public void customize(UndertowServletWebServerFactory factory) {
		factory.setEagerFilterInit(this.serverProperties.getUndertow().isEagerFilterInit());
		factory.setPreservePathOnForward(this.serverProperties.getUndertow().isPreservePathOnForward());
	}

}
