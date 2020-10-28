package org.springframework.boot.autoconfigure.websocket.servlet;

import org.apache.tomcat.websocket.server.WsSci;

import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.boot.web.server.WebServerFactoryCustomizer;
import org.springframework.core.Ordered;

/**
 * WebSocket customizer for {@link TomcatServletWebServerFactory}.
 *



 * @since 2.0.0
 */
public class TomcatWebSocketServletWebServerCustomizer
		implements WebServerFactoryCustomizer<TomcatServletWebServerFactory>, Ordered {

	@Override
	public void customize(TomcatServletWebServerFactory factory) {
		factory.addContextCustomizers((context) -> context.addServletContainerInitializer(new WsSci(), null));
	}

	@Override
	public int getOrder() {
		return 0;
	}

}
