package org.springframework.boot.devtools.tunnel.server;

import java.io.IOException;
import java.nio.channels.ByteChannel;

/**
 * Manages the connection to the ultimate tunnel target server.
 *

 * @since 1.3.0
 */
@FunctionalInterface
public interface TargetServerConnection {

	/**
	 * Open a connection to the target server with the specified timeout.
	 * @param timeout the read timeout
	 * @return a {@link ByteChannel} providing read/write access to the server
	 * @throws IOException in case of I/O errors
	 */
	ByteChannel open(int timeout) throws IOException;

}
