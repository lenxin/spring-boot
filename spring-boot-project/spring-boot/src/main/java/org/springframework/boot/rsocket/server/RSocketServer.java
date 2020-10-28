package org.springframework.boot.rsocket.server;

import java.net.InetSocketAddress;

/**
 * Simple interface that represents a fully configured RSocket server. Allows the server
 * to be {@link #start() started} and {@link #stop() stopped}.
 *

 * @since 2.2.0
 */
public interface RSocketServer {

	/**
	 * Starts the RSocket server. Calling this method on an already started server has no
	 * effect.
	 * @throws RSocketServerException if the server cannot be started
	 */
	void start() throws RSocketServerException;

	/**
	 * Stops the RSocket server. Calling this method on an already stopped server has no
	 * effect.
	 * @throws RSocketServerException if the server cannot be stopped
	 */
	void stop() throws RSocketServerException;

	/**
	 * Return the address this server is listening on.
	 * @return the address
	 */
	InetSocketAddress address();

	/**
	 * Choice of transport protocol for the RSocket server.
	 */
	enum Transport {

		/**
		 * TCP transport protocol.
		 */
		TCP,

		/**
		 * WebSocket transport protocol.
		 */
		WEBSOCKET

	}

}
