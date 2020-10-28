package org.springframework.boot.autoconfigure.rsocket;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import io.rsocket.SocketAcceptor;
import io.rsocket.core.RSocketServer;
import io.rsocket.transport.ServerTransport;
import io.rsocket.transport.netty.server.WebsocketRouteTransport;
import reactor.netty.http.server.HttpServerRoutes;

import org.springframework.boot.rsocket.server.RSocketServerCustomizer;
import org.springframework.boot.web.embedded.netty.NettyRouteProvider;

/**
 * {@link NettyRouteProvider} that configures an RSocket Websocket endpoint.
 *

 */
class RSocketWebSocketNettyRouteProvider implements NettyRouteProvider {

	private final String mappingPath;

	private final SocketAcceptor socketAcceptor;

	private final List<RSocketServerCustomizer> customizers;

	RSocketWebSocketNettyRouteProvider(String mappingPath, SocketAcceptor socketAcceptor,
			Stream<RSocketServerCustomizer> customizers) {
		this.mappingPath = mappingPath;
		this.socketAcceptor = socketAcceptor;
		this.customizers = customizers.collect(Collectors.toList());
	}

	@Override
	public HttpServerRoutes apply(HttpServerRoutes httpServerRoutes) {
		RSocketServer server = RSocketServer.create(this.socketAcceptor);
		this.customizers.forEach((customizer) -> customizer.customize(server));
		ServerTransport.ConnectionAcceptor connectionAcceptor = server.asConnectionAcceptor();
		return httpServerRoutes.ws(this.mappingPath, WebsocketRouteTransport.newHandler(connectionAcceptor));
	}

}
