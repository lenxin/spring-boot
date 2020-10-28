package org.springframework.boot.devtools.remote.server;

import org.springframework.http.server.ServerHttpRequest;
import org.springframework.util.Assert;

/**
 * {@link AccessManager} that checks for the presence of a HTTP header secret.
 *


 * @since 1.3.0
 */
public class HttpHeaderAccessManager implements AccessManager {

	private final String headerName;

	private final String expectedSecret;

	public HttpHeaderAccessManager(String headerName, String expectedSecret) {
		Assert.hasLength(headerName, "HeaderName must not be empty");
		Assert.hasLength(expectedSecret, "ExpectedSecret must not be empty");
		this.headerName = headerName;
		this.expectedSecret = expectedSecret;
	}

	@Override
	public boolean isAllowed(ServerHttpRequest request) {
		String providedSecret = request.getHeaders().getFirst(this.headerName);
		return this.expectedSecret.equals(providedSecret);
	}

}
