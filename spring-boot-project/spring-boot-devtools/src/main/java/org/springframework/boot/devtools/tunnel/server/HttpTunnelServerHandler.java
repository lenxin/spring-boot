package org.springframework.boot.devtools.tunnel.server;

import java.io.IOException;

import org.springframework.boot.devtools.remote.server.Handler;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.util.Assert;

/**
 * Adapts a {@link HttpTunnelServer} to a {@link Handler}.
 *

 * @since 1.3.0
 */
public class HttpTunnelServerHandler implements Handler {

	private HttpTunnelServer server;

	/**
	 * Create a new {@link HttpTunnelServerHandler} instance.
	 * @param server the server to adapt
	 */
	public HttpTunnelServerHandler(HttpTunnelServer server) {
		Assert.notNull(server, "Server must not be null");
		this.server = server;
	}

	@Override
	public void handle(ServerHttpRequest request, ServerHttpResponse response) throws IOException {
		this.server.handle(request, response);
	}

}
