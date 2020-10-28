package org.springframework.boot.web.embedded.netty;

import java.util.function.Function;

import reactor.netty.http.server.HttpServer;

/**
 * Mapping function that can be used to customize a Reactor Netty server instance.
 *

 * @see NettyReactiveWebServerFactory
 * @since 2.1.0
 */
@FunctionalInterface
public interface NettyServerCustomizer extends Function<HttpServer, HttpServer> {

}
