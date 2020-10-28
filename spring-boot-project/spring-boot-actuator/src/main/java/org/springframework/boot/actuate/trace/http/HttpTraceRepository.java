package org.springframework.boot.actuate.trace.http;

import java.util.List;

/**
 * A repository for {@link HttpTrace}s.
 *


 * @since 2.0.0
 */
public interface HttpTraceRepository {

	/**
	 * Find all {@link HttpTrace} objects contained in the repository.
	 * @return the results
	 */
	List<HttpTrace> findAll();

	/**
	 * Adds a trace to the repository.
	 * @param trace the trace to add
	 */
	void add(HttpTrace trace);

}
