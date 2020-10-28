package org.springframework.boot.autoconfigure.websocket.reactive;

import org.apache.tomcat.websocket.server.WsSci;

import org.springframework.boot.web.embedded.tomcat.TomcatReactiveWebServerFactory;
import org.springframework.boot.web.server.WebServerFactoryCustomizer;
import org.springframework.core.Ordered;

/**
 * WebSocket customizer for {@link TomcatReactiveWebServerFactory}.
 *

 * @since 2.0.0
 */
public class TomcatWebSocketReactiveWebServerCustomizer
		implements WebServerFactoryCustomizer<TomcatReactiveWebServerFactory>, Ordered {

	@Override
	public void customize(TomcatReactiveWebServerFactory factory) {
		factory.addContextCustomizers((context) -> context.addServletContainerInitializer(new WsSci(), null));
	}

	@Override
	public int getOrder() {
		return 0;
	}

}
