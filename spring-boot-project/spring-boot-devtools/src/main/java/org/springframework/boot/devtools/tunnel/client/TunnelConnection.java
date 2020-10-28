package org.springframework.boot.devtools.tunnel.client;

import java.io.Closeable;
import java.nio.channels.WritableByteChannel;

/**
 * Interface used to manage socket tunnel connections.
 *

 * @since 1.3.0
 */
@FunctionalInterface
public interface TunnelConnection {

	/**
	 * Open the tunnel connection.
	 * @param incomingChannel a {@link WritableByteChannel} that should be used to write
	 * any incoming data received from the remote server
	 * @param closeable a closeable to call when the channel is closed
	 * @return a {@link WritableByteChannel} that should be used to send any outgoing data
	 * destined for the remote server
	 * @throws Exception in case of errors
	 */
	WritableByteChannel open(WritableByteChannel incomingChannel, Closeable closeable) throws Exception;

}
