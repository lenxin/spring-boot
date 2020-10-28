package org.springframework.boot.web.embedded.jetty;

import org.eclipse.jetty.server.ConnectionFactory;
import org.eclipse.jetty.server.Connector;
import org.eclipse.jetty.server.ForwardedRequestCustomizer;
import org.eclipse.jetty.server.HttpConfiguration;
import org.eclipse.jetty.server.Server;

/**
 * {@link JettyServerCustomizer} to add {@link ForwardedRequestCustomizer}.
 *

 */
class ForwardHeadersCustomizer implements JettyServerCustomizer {

	@Override
	public void customize(Server server) {
		ForwardedRequestCustomizer customizer = new ForwardedRequestCustomizer();
		for (Connector connector : server.getConnectors()) {
			for (ConnectionFactory connectionFactory : connector.getConnectionFactories()) {
				if (connectionFactory instanceof HttpConfiguration.ConnectionFactory) {
					((HttpConfiguration.ConnectionFactory) connectionFactory).getHttpConfiguration()
							.addCustomizer(customizer);
				}
			}
		}
	}

}
