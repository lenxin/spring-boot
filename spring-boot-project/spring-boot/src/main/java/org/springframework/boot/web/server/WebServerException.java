package org.springframework.boot.web.server;

/**
 * Exceptions thrown by an web server.
 *

 * @since 2.0.0
 */
@SuppressWarnings("serial")
public class WebServerException extends RuntimeException {

	public WebServerException(String message, Throwable cause) {
		super(message, cause);
	}

}
