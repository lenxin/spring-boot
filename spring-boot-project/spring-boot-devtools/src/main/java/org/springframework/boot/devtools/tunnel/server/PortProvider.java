package org.springframework.boot.devtools.tunnel.server;

/**
 * Strategy interface to provide access to a port (which may change if an existing
 * connection is closed).
 *

 * @since 1.3.0
 */
@FunctionalInterface
public interface PortProvider {

	/**
	 * Return the port number.
	 * @return the port number
	 */
	int getPort();

}
