package org.springframework.boot.devtools.tunnel.client;

import java.nio.channels.SocketChannel;

/**
 * Listener that can be used to receive {@link TunnelClient} events.
 *

 * @since 1.3.0
 */
public interface TunnelClientListener {

	/**
	 * Called when a socket channel is opened.
	 * @param socket the socket channel
	 */
	void onOpen(SocketChannel socket);

	/**
	 * Called when a socket channel is closed.
	 * @param socket the socket channel
	 */
	void onClose(SocketChannel socket);

}
