package org.springframework.boot.autoconfigure.web.reactive;

import org.springframework.boot.autoconfigure.web.ServerProperties;
import org.springframework.boot.web.embedded.tomcat.TomcatReactiveWebServerFactory;
import org.springframework.boot.web.server.WebServerFactoryCustomizer;

/**
 * {@link WebServerFactoryCustomizer} to apply {@link ServerProperties} to Tomcat reactive
 * web servers.
 *

 * @since 2.2.0
 */
public class TomcatReactiveWebServerFactoryCustomizer
		implements WebServerFactoryCustomizer<TomcatReactiveWebServerFactory> {

	private final ServerProperties serverProperties;

	public TomcatReactiveWebServerFactoryCustomizer(ServerProperties serverProperties) {
		this.serverProperties = serverProperties;
	}

	@Override
	public void customize(TomcatReactiveWebServerFactory factory) {
		factory.setDisableMBeanRegistry(!this.serverProperties.getTomcat().getMbeanregistry().isEnabled());
	}

}
