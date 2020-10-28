package org.springframework.boot.test.autoconfigure.web.reactive.webclient;

import reactor.core.publisher.Mono;

import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.web.reactive.error.ErrorWebExceptionHandler;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebExceptionHandler;

/**
 * Example {@link WebExceptionHandler} used with {@link WebFluxTest @WebFluxTest} tests.
 *

 */
@Component
@Order(-2)
public class ExampleWebExceptionHandler implements WebExceptionHandler {

	private final ErrorWebExceptionHandler fallback;

	public ExampleWebExceptionHandler(ErrorWebExceptionHandler fallback) {
		this.fallback = fallback;
	}

	@Override
	public Mono<Void> handle(ServerWebExchange exchange, Throwable ex) {
		if (ex instanceof RuntimeException && "foo".equals(ex.getMessage())) {
			exchange.getResponse().setStatusCode(HttpStatus.BAD_REQUEST);
			return exchange.getResponse().setComplete();
		}
		return this.fallback.handle(exchange, ex);
	}

}
