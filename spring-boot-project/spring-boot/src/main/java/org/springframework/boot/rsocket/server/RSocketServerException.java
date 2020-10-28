package org.springframework.boot.rsocket.server;

/**
 * Exceptions thrown by an RSocket server.
 *

 * @since 2.2.0
 */
public class RSocketServerException extends RuntimeException {

	public RSocketServerException(String message, Throwable cause) {
		super(message, cause);
	}

}
