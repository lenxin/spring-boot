package org.springframework.boot.autoconfigure.websocket.servlet;

import org.eclipse.jetty.util.thread.ShutdownThread;
import org.eclipse.jetty.webapp.AbstractConfiguration;
import org.eclipse.jetty.webapp.WebAppContext;
import org.eclipse.jetty.websocket.jsr356.server.ServerContainer;
import org.eclipse.jetty.websocket.jsr356.server.deploy.WebSocketServerContainerInitializer;

import org.springframework.boot.web.embedded.jetty.JettyServletWebServerFactory;
import org.springframework.boot.web.server.WebServerFactoryCustomizer;
import org.springframework.core.Ordered;

/**
 * WebSocket customizer for {@link JettyServletWebServerFactory}.
 *



 * @since 2.0.0
 */
public class JettyWebSocketServletWebServerCustomizer
		implements WebServerFactoryCustomizer<JettyServletWebServerFactory>, Ordered {

	@Override
	public void customize(JettyServletWebServerFactory factory) {
		factory.addConfigurations(new AbstractConfiguration() {

			@Override
			public void configure(WebAppContext context) throws Exception {
				ServerContainer serverContainer = WebSocketServerContainerInitializer.initialize(context);
				ShutdownThread.deregister(serverContainer);
			}

		});
	}

	@Override
	public int getOrder() {
		return 0;
	}

}
