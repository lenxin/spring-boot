package org.springframework.boot.devtools.tunnel.server;

import org.junit.jupiter.api.Test;

import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;

import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

/**
 * Tests for {@link HttpTunnelServerHandler}.
 *

 */
class HttpTunnelServerHandlerTests {

	@Test
	void serverMustNotBeNull() {
		assertThatIllegalArgumentException().isThrownBy(() -> new HttpTunnelServerHandler(null))
				.withMessageContaining("Server must not be null");
	}

	@Test
	void handleDelegatesToServer() throws Exception {
		HttpTunnelServer server = mock(HttpTunnelServer.class);
		HttpTunnelServerHandler handler = new HttpTunnelServerHandler(server);
		ServerHttpRequest request = mock(ServerHttpRequest.class);
		ServerHttpResponse response = mock(ServerHttpResponse.class);
		handler.handle(request, response);
		verify(server).handle(request, response);
	}

}
