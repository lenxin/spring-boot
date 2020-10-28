package org.springframework.boot.autoconfigure.websocket.servlet;

import io.undertow.servlet.api.DeploymentInfo;
import io.undertow.websockets.jsr.WebSocketDeploymentInfo;

import org.springframework.boot.web.embedded.undertow.UndertowDeploymentInfoCustomizer;
import org.springframework.boot.web.embedded.undertow.UndertowServletWebServerFactory;
import org.springframework.boot.web.server.WebServerFactoryCustomizer;
import org.springframework.core.Ordered;

/**
 * WebSocket customizer for {@link UndertowServletWebServerFactory}.
 *

 * @since 2.0.0
 */
public class UndertowWebSocketServletWebServerCustomizer
		implements WebServerFactoryCustomizer<UndertowServletWebServerFactory>, Ordered {

	@Override
	public void customize(UndertowServletWebServerFactory factory) {
		WebsocketDeploymentInfoCustomizer customizer = new WebsocketDeploymentInfoCustomizer();
		factory.addDeploymentInfoCustomizers(customizer);
	}

	@Override
	public int getOrder() {
		return 0;
	}

	private static class WebsocketDeploymentInfoCustomizer implements UndertowDeploymentInfoCustomizer {

		@Override
		public void customize(DeploymentInfo deploymentInfo) {
			WebSocketDeploymentInfo info = new WebSocketDeploymentInfo();
			deploymentInfo.addServletContextAttribute(WebSocketDeploymentInfo.ATTRIBUTE_NAME, info);
		}

	}

}
