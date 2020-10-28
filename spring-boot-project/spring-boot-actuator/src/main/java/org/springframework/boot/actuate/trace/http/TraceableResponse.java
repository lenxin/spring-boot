package org.springframework.boot.actuate.trace.http;

import java.util.List;
import java.util.Map;

/**
 * A representation of an HTTP response that is suitable for tracing.
 *

 * @since 2.0.0
 * @see HttpExchangeTracer
 */
public interface TraceableResponse {

	/**
	 * The status of the response.
	 * @return the status
	 */
	int getStatus();

	/**
	 * Returns a modifiable copy of the headers of the response.
	 * @return the headers
	 */
	Map<String, List<String>> getHeaders();

}
