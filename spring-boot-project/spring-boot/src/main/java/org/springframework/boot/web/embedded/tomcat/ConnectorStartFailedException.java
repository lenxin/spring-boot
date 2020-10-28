package org.springframework.boot.web.embedded.tomcat;

import org.apache.catalina.connector.Connector;

import org.springframework.boot.web.server.WebServerException;

/**
 * A {@code ConnectorStartFailedException} is thrown when a Tomcat {@link Connector} fails
 * to start, for example due to a port clash or incorrect SSL configuration.
 *

 * @since 2.0.0
 */
public class ConnectorStartFailedException extends WebServerException {

	private final int port;

	/**
	 * Creates a new {@code ConnectorStartFailedException} for a connector that's
	 * configured to listen on the given {@code port}.
	 * @param port the port
	 */
	public ConnectorStartFailedException(int port) {
		super("Connector configured to listen on port " + port + " failed to start", null);
		this.port = port;
	}

	public int getPort() {
		return this.port;
	}

}
