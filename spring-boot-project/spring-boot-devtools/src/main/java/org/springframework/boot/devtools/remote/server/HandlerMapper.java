package org.springframework.boot.devtools.remote.server;

import org.springframework.http.server.ServerHttpRequest;

/**
 * Interface to provide a mapping between a {@link ServerHttpRequest} and a
 * {@link Handler}.
 *

 * @since 1.3.0
 */
@FunctionalInterface
public interface HandlerMapper {

	/**
	 * Return the handler for the given request or {@code null}.
	 * @param request the request
	 * @return a {@link Handler} or {@code null}
	 */
	Handler getHandler(ServerHttpRequest request);

}
