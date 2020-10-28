package org.springframework.boot.web.embedded.netty;

import java.util.function.Function;

import reactor.netty.http.server.HttpServerRoutes;

/**
 * Function that can add new routes to an {@link HttpServerRoutes} instance.
 *

 * @see NettyReactiveWebServerFactory
 * @since 2.2.0
 */
@FunctionalInterface
public interface NettyRouteProvider extends Function<HttpServerRoutes, HttpServerRoutes> {

}
