package org.springframework.boot.web.servlet.filter;

import javax.servlet.Filter;

import org.springframework.core.Ordered;

/**
 * An {@link Ordered} {@link javax.servlet.Filter}.
 *

 * @since 2.1.0
 */
public interface OrderedFilter extends Filter, Ordered {

	/**
	 * Filters that wrap the servlet request should be ordered less than or equal to this.
	 */
	int REQUEST_WRAPPER_FILTER_MAX_ORDER = 0;

}
