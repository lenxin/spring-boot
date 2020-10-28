package org.springframework.boot.devtools.remote.server;

import org.springframework.http.server.ServerHttpRequest;
import org.springframework.util.Assert;

/**
 * {@link HandlerMapper} implementation that maps incoming URLs.
 *


 * @since 1.3.0
 */
public class UrlHandlerMapper implements HandlerMapper {

	private final String requestUri;

	private final Handler handler;

	/**
	 * Create a new {@link UrlHandlerMapper}.
	 * @param url the URL to map
	 * @param handler the handler to use
	 */
	public UrlHandlerMapper(String url, Handler handler) {
		Assert.hasLength(url, "URL must not be empty");
		Assert.isTrue(url.startsWith("/"), "URL must start with '/'");
		this.requestUri = url;
		this.handler = handler;
	}

	@Override
	public Handler getHandler(ServerHttpRequest request) {
		if (this.requestUri.equals(request.getURI().getPath())) {
			return this.handler;
		}
		return null;
	}

}
