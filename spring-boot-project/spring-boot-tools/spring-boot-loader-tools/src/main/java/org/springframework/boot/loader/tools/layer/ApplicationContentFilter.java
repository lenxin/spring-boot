package org.springframework.boot.loader.tools.layer;

import org.springframework.util.AntPathMatcher;
import org.springframework.util.Assert;

/**
 * {@link ContentFilter} that matches application items based on an Ant-style path
 * pattern.
 *


 * @since 2.3.0
 */
public class ApplicationContentFilter implements ContentFilter<String> {

	private static final AntPathMatcher MATCHER = new AntPathMatcher();

	private final String pattern;

	public ApplicationContentFilter(String pattern) {
		Assert.hasText(pattern, "Pattern must not be empty");
		this.pattern = pattern;
	}

	@Override
	public boolean matches(String path) {
		return MATCHER.match(this.pattern, path);
	}

}
