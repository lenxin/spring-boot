package org.springframework.boot.actuate.trace.http;

import java.net.URI;
import java.util.List;
import java.util.Map;

/**
 * A representation of an HTTP request that is suitable for tracing.
 *

 * @since 2.0.0
 * @see HttpExchangeTracer
 */
public interface TraceableRequest {

	/**
	 * Returns the method (GET, POST, etc) of the request.
	 * @return the method
	 */
	String getMethod();

	/**
	 * Returns the URI of the request.
	 * @return the URI
	 */
	URI getUri();

	/**
	 * Returns a modifiable copy of the headers of the request.
	 * @return the headers
	 */
	Map<String, List<String>> getHeaders();

	/**
	 * Returns the remote address from which the request was sent, if available.
	 * @return the remote address or {@code null}
	 */
	String getRemoteAddress();

}
