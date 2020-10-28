package org.springframework.boot.devtools.remote.server;

import org.springframework.http.server.ServerHttpRequest;

/**
 * Provides access control for a {@link Dispatcher}.
 *

 * @since 1.3.0
 */
@FunctionalInterface
public interface AccessManager {

	/**
	 * {@link AccessManager} that permits all requests.
	 */
	AccessManager PERMIT_ALL = (request) -> true;

	/**
	 * Determine if the specific request is allowed to be handled by the
	 * {@link Dispatcher}.
	 * @param request the request to check
	 * @return {@code true} if access is allowed.
	 */
	boolean isAllowed(ServerHttpRequest request);

}
