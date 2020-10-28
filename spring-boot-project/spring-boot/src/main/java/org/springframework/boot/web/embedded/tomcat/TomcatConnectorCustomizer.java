package org.springframework.boot.web.embedded.tomcat;

import org.apache.catalina.connector.Connector;

/**
 * Callback interface that can be used to customize a Tomcat {@link Connector}.
 *

 * @see ConfigurableTomcatWebServerFactory
 * @since 2.0.0
 */
@FunctionalInterface
public interface TomcatConnectorCustomizer {

	/**
	 * Customize the connector.
	 * @param connector the connector to customize
	 */
	void customize(Connector connector);

}
