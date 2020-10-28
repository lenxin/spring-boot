package org.springframework.boot.web.embedded.undertow;

import java.io.Closeable;

import io.undertow.server.HttpHandler;
import io.undertow.server.handlers.GracefulShutdownHandler;

/**
 * Factory used by {@link UndertowServletWebServer} to add {@link HttpHandler
 * HttpHandlers}. Instances returned from this factory may optionally implement the
 * following interfaces:
 * <ul>
 * <li>{@link Closeable} - if they wish to be closed just before server stops.</li>
 * <li>{@link GracefulShutdownHandler} - if they wish to manage graceful shutdown.</li>
 * </ul>
 *

 * @since 2.3.0
 */
@FunctionalInterface
public interface HttpHandlerFactory {

	/**
	 * Create the {@link HttpHandler} instance that should be added.
	 * @param next the next handler in the chain
	 * @return the new HTTP handler instance
	 */
	HttpHandler getHandler(HttpHandler next);

}
