package org.springframework.boot.devtools.tunnel.client;

import java.nio.channels.SocketChannel;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import org.springframework.util.Assert;

/**
 * A collection of {@link TunnelClientListener}.
 *

 */
class TunnelClientListeners {

	private final List<TunnelClientListener> listeners = new CopyOnWriteArrayList<>();

	void addListener(TunnelClientListener listener) {
		Assert.notNull(listener, "Listener must not be null");
		this.listeners.add(listener);
	}

	void removeListener(TunnelClientListener listener) {
		Assert.notNull(listener, "Listener must not be null");
		this.listeners.remove(listener);
	}

	void fireOpenEvent(SocketChannel socket) {
		for (TunnelClientListener listener : this.listeners) {
			listener.onOpen(socket);
		}
	}

	void fireCloseEvent(SocketChannel socket) {
		for (TunnelClientListener listener : this.listeners) {
			listener.onClose(socket);
		}
	}

}
