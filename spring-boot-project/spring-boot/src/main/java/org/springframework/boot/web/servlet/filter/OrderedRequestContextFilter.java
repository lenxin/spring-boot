package org.springframework.boot.web.servlet.filter;

import org.springframework.core.Ordered;
import org.springframework.web.filter.RequestContextFilter;

/**
 * {@link RequestContextFilter} that also implements {@link Ordered}.
 *

 * @since 2.0.0
 */
public class OrderedRequestContextFilter extends RequestContextFilter implements OrderedFilter {

	// Order defaults to after Spring Session filter
	private int order = REQUEST_WRAPPER_FILTER_MAX_ORDER - 105;

	@Override
	public int getOrder() {
		return this.order;
	}

	/**
	 * Set the order for this filter.
	 * @param order the order to set
	 */
	public void setOrder(int order) {
		this.order = order;
	}

}
