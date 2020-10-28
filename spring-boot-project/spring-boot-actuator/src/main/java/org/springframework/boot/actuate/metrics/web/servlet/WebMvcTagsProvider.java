package org.springframework.boot.actuate.metrics.web.servlet;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import io.micrometer.core.instrument.LongTaskTimer;
import io.micrometer.core.instrument.Tag;

/**
 * Provides {@link Tag Tags} for Spring MVC-based request handling.
 *


 * @since 2.0.0
 */
public interface WebMvcTagsProvider {

	/**
	 * Provides tags to be associated with metrics for the given {@code request} and
	 * {@code response} exchange.
	 * @param request the request
	 * @param response the response
	 * @param handler the handler for the request or {@code null} if the handler is
	 * unknown
	 * @param exception the current exception, if any
	 * @return tags to associate with metrics for the request and response exchange
	 */
	Iterable<Tag> getTags(HttpServletRequest request, HttpServletResponse response, Object handler,
			Throwable exception);

	/**
	 * Provides tags to be used by {@link LongTaskTimer long task timers}.
	 * @param request the HTTP request
	 * @param handler the handler for the request or {@code null} if the handler is
	 * unknown
	 * @return tags to associate with metrics recorded for the request
	 */
	Iterable<Tag> getLongRequestTags(HttpServletRequest request, Object handler);

}
