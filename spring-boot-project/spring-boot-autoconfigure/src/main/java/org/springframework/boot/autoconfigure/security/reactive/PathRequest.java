package org.springframework.boot.autoconfigure.security.reactive;

import org.springframework.boot.autoconfigure.security.StaticResourceLocation;
import org.springframework.security.web.server.util.matcher.ServerWebExchangeMatcher;

/**
 * Factory that can be used to create a {@link ServerWebExchangeMatcher} for commonly used
 * paths.
 *

 * @since 2.0.0
 */
public final class PathRequest {

	private PathRequest() {
	}

	/**
	 * Returns a {@link StaticResourceRequest} that can be used to create a matcher for
	 * {@link StaticResourceLocation locations}.
	 * @return a {@link StaticResourceRequest}
	 */
	public static StaticResourceRequest toStaticResources() {
		return StaticResourceRequest.INSTANCE;
	}

}
