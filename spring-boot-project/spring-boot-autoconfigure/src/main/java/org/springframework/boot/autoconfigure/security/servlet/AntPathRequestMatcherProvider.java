package org.springframework.boot.autoconfigure.security.servlet;

import java.util.function.Function;

import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;

/**
 * {@link RequestMatcherProvider} that provides an {@link AntPathRequestMatcher}.
 *

 * @since 2.1.8
 */
public class AntPathRequestMatcherProvider implements RequestMatcherProvider {

	private final Function<String, String> pathFactory;

	public AntPathRequestMatcherProvider(Function<String, String> pathFactory) {
		this.pathFactory = pathFactory;
	}

	@Override
	public RequestMatcher getRequestMatcher(String pattern) {
		return new AntPathRequestMatcher(this.pathFactory.apply(pattern));
	}

}
