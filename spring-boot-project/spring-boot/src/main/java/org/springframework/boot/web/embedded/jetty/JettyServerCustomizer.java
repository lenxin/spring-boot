package org.springframework.boot.web.embedded.jetty;

import org.eclipse.jetty.server.Server;

/**
 * Callback interface that can be used to customize a Jetty {@link Server}.
 *

 * @see JettyServletWebServerFactory
 * @since 2.0.0
 */
@FunctionalInterface
public interface JettyServerCustomizer {

	/**
	 * Customize the server.
	 * @param server the server to customize
	 */
	void customize(Server server);

}
