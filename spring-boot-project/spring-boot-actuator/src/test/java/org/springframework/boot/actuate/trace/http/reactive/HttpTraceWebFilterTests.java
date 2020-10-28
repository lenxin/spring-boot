package org.springframework.boot.actuate.trace.http.reactive;

import java.security.Principal;
import java.time.Duration;
import java.util.EnumSet;

import org.junit.jupiter.api.Test;
import reactor.core.publisher.Mono;

import org.springframework.boot.actuate.trace.http.HttpExchangeTracer;
import org.springframework.boot.actuate.trace.http.HttpTrace.Session;
import org.springframework.boot.actuate.trace.http.InMemoryHttpTraceRepository;
import org.springframework.boot.actuate.trace.http.Include;
import org.springframework.boot.actuate.web.trace.reactive.HttpTraceWebFilter;
import org.springframework.mock.http.server.reactive.MockServerHttpRequest;
import org.springframework.mock.web.server.MockServerWebExchange;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.ServerWebExchangeDecorator;
import org.springframework.web.server.WebFilterChain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

/**
 * Tests for {@link HttpTraceWebFilter}.
 *

 */
class HttpTraceWebFilterTests {

	private final InMemoryHttpTraceRepository repository = new InMemoryHttpTraceRepository();

	private final HttpExchangeTracer tracer = new HttpExchangeTracer(EnumSet.allOf(Include.class));

	private final HttpTraceWebFilter filter = new HttpTraceWebFilter(this.repository, this.tracer,
			EnumSet.allOf(Include.class));

	@Test
	void filterTracesExchange() {
		executeFilter(MockServerWebExchange.from(MockServerHttpRequest.get("https://api.example.com")),
				(exchange) -> Mono.empty()).block(Duration.ofSeconds(30));
		assertThat(this.repository.findAll()).hasSize(1);
	}

	@Test
	void filterCapturesSessionIdWhenSessionIsUsed() {
		executeFilter(MockServerWebExchange.from(MockServerHttpRequest.get("https://api.example.com")), (exchange) -> {
			exchange.getSession().block(Duration.ofSeconds(30)).getAttributes().put("a", "alpha");
			return Mono.empty();
		}).block(Duration.ofSeconds(30));
		assertThat(this.repository.findAll()).hasSize(1);
		Session session = this.repository.findAll().get(0).getSession();
		assertThat(session).isNotNull();
		assertThat(session.getId()).isNotNull();
	}

	@Test
	void filterDoesNotCaptureIdOfUnusedSession() {
		executeFilter(MockServerWebExchange.from(MockServerHttpRequest.get("https://api.example.com")), (exchange) -> {
			exchange.getSession().block(Duration.ofSeconds(30));
			return Mono.empty();
		}).block(Duration.ofSeconds(30));
		assertThat(this.repository.findAll()).hasSize(1);
		Session session = this.repository.findAll().get(0).getSession();
		assertThat(session).isNull();
	}

	@Test
	void filterCapturesPrincipal() {
		Principal principal = mock(Principal.class);
		given(principal.getName()).willReturn("alice");
		executeFilter(new ServerWebExchangeDecorator(
				MockServerWebExchange.from(MockServerHttpRequest.get("https://api.example.com"))) {

			@SuppressWarnings("unchecked")
			@Override
			public <T extends Principal> Mono<T> getPrincipal() {
				return Mono.just((T) principal);
			}

		}, (exchange) -> {
			exchange.getSession().block(Duration.ofSeconds(30)).getAttributes().put("a", "alpha");
			return Mono.empty();
		}).block(Duration.ofSeconds(30));
		assertThat(this.repository.findAll()).hasSize(1);
		org.springframework.boot.actuate.trace.http.HttpTrace.Principal tracedPrincipal = this.repository.findAll()
				.get(0).getPrincipal();
		assertThat(tracedPrincipal).isNotNull();
		assertThat(tracedPrincipal.getName()).isEqualTo("alice");
	}

	private Mono<Void> executeFilter(ServerWebExchange exchange, WebFilterChain chain) {
		return this.filter.filter(exchange, chain).then(Mono.defer(() -> exchange.getResponse().setComplete()));
	}

}
