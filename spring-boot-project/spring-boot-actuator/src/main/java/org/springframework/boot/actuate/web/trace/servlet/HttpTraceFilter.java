package org.springframework.boot.actuate.web.trace.servlet;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;
import javax.servlet.http.HttpSession;

import org.springframework.boot.actuate.trace.http.HttpExchangeTracer;
import org.springframework.boot.actuate.trace.http.HttpTrace;
import org.springframework.boot.actuate.trace.http.HttpTraceRepository;
import org.springframework.core.Ordered;
import org.springframework.http.HttpStatus;
import org.springframework.web.filter.OncePerRequestFilter;

/**
 * Servlet {@link Filter} that logs all requests to an {@link HttpTraceRepository}.
 *





 * @since 2.0.0
 */
public class HttpTraceFilter extends OncePerRequestFilter implements Ordered {

	// Not LOWEST_PRECEDENCE, but near the end, so it has a good chance of catching all
	// enriched headers, but users can add stuff after this if they want to
	private int order = Ordered.LOWEST_PRECEDENCE - 10;

	private final HttpTraceRepository repository;

	private final HttpExchangeTracer tracer;

	/**
	 * Create a new {@link HttpTraceFilter} instance.
	 * @param repository the trace repository
	 * @param tracer used to trace exchanges
	 */
	public HttpTraceFilter(HttpTraceRepository repository, HttpExchangeTracer tracer) {
		this.repository = repository;
		this.tracer = tracer;
	}

	@Override
	public int getOrder() {
		return this.order;
	}

	public void setOrder(int order) {
		this.order = order;
	}

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		if (!isRequestValid(request)) {
			filterChain.doFilter(request, response);
			return;
		}
		TraceableHttpServletRequest traceableRequest = new TraceableHttpServletRequest(request);
		HttpTrace trace = this.tracer.receivedRequest(traceableRequest);
		int status = HttpStatus.INTERNAL_SERVER_ERROR.value();
		try {
			filterChain.doFilter(request, response);
			status = response.getStatus();
		}
		finally {
			TraceableHttpServletResponse traceableResponse = new TraceableHttpServletResponse(
					(status != response.getStatus()) ? new CustomStatusResponseWrapper(response, status) : response);
			this.tracer.sendingResponse(trace, traceableResponse, request::getUserPrincipal,
					() -> getSessionId(request));
			this.repository.add(trace);
		}
	}

	private boolean isRequestValid(HttpServletRequest request) {
		try {
			new URI(request.getRequestURL().toString());
			return true;
		}
		catch (URISyntaxException ex) {
			return false;
		}
	}

	private String getSessionId(HttpServletRequest request) {
		HttpSession session = request.getSession(false);
		return (session != null) ? session.getId() : null;
	}

	private static final class CustomStatusResponseWrapper extends HttpServletResponseWrapper {

		private final int status;

		private CustomStatusResponseWrapper(HttpServletResponse response, int status) {
			super(response);
			this.status = status;
		}

		@Override
		public int getStatus() {
			return this.status;
		}

	}

}
