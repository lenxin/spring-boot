package org.springframework.boot.web.reactive.filter;

import org.springframework.core.Ordered;
import org.springframework.web.server.WebFilter;

/**
 * An {@link Ordered} {@link org.springframework.web.server.WebFilter}.
 *

 * @since 2.1.0
 */
public interface OrderedWebFilter extends WebFilter, Ordered {

	/**
	 * Filters that wrap the request should be ordered less than or equal to this.
	 */
	int REQUEST_WRAPPER_FILTER_MAX_ORDER = 0;

}
