package org.springframework.boot.web.reactive.filter;

import org.springframework.core.Ordered;
import org.springframework.web.filter.reactive.HiddenHttpMethodFilter;

/**
 * {@link HiddenHttpMethodFilter} that also implements {@link Ordered}.
 *

 * @since 2.0.5
 */
public class OrderedHiddenHttpMethodFilter extends HiddenHttpMethodFilter implements OrderedWebFilter {

	/**
	 * The default order is high to ensure the filter is applied before Spring Security.
	 */
	public static final int DEFAULT_ORDER = REQUEST_WRAPPER_FILTER_MAX_ORDER - 10000;

	private int order = DEFAULT_ORDER;

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
