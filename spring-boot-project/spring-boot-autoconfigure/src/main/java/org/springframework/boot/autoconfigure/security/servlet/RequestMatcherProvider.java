package org.springframework.boot.autoconfigure.security.servlet;

import org.springframework.security.web.util.matcher.RequestMatcher;

/**
 * Interface that can be used to provide a {@link RequestMatcher} that can be used with
 * Spring Security.
 *

 * @since 2.0.5
 */
@FunctionalInterface
public interface RequestMatcherProvider {

	/**
	 * Return the {@link RequestMatcher} to be used for the specified pattern.
	 * @param pattern the request pattern
	 * @return a request matcher
	 */
	RequestMatcher getRequestMatcher(String pattern);

}
