package org.springframework.boot.actuate.endpoint.web.servlet;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.HandlerInterceptor;

/**
 * {@link HandlerInterceptor} to ensure that
 * {@link org.springframework.web.accept.PathExtensionContentNegotiationStrategy} is
 * skipped for web endpoints.
 *

 */
final class SkipPathExtensionContentNegotiation implements HandlerInterceptor {

	@SuppressWarnings("deprecation")
	private static final String SKIP_ATTRIBUTE = org.springframework.web.accept.PathExtensionContentNegotiationStrategy.class
			.getName() + ".SKIP";

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		request.setAttribute(SKIP_ATTRIBUTE, Boolean.TRUE);
		return true;
	}

}
