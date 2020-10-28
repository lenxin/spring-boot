package org.springframework.boot.rsocket.server;

import io.rsocket.core.RSocketServer;

/**
 * Callback interface that can be used to customize a {@link RSocketServer}.
 *

 * @see RSocketServer
 * @since 2.3.0
 */
@FunctionalInterface
public interface RSocketServerCustomizer {

	/**
	 * Callback to customize a {@link RSocketServer} instance.
	 * @param rSocketServer the RSocket server to customize
	 */
	void customize(RSocketServer rSocketServer);

}
