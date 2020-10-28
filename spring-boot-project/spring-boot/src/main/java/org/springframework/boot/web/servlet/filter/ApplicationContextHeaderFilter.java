package org.springframework.boot.web.servlet.filter;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.context.ApplicationContext;
import org.springframework.web.filter.OncePerRequestFilter;

/**
 * {@link OncePerRequestFilter} to add an {@literal X-Application-Context} header that
 * contains the {@link ApplicationContext#getId() ApplicationContext ID}.
 *


 * @since 2.0.0
 */
public class ApplicationContextHeaderFilter extends OncePerRequestFilter {

	/**
	 * Public constant for {@literal X-Application-Context}.
	 */
	public static final String HEADER_NAME = "X-Application-Context";

	private final ApplicationContext applicationContext;

	public ApplicationContextHeaderFilter(ApplicationContext context) {
		this.applicationContext = context;
	}

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		response.addHeader(HEADER_NAME, this.applicationContext.getId());
		filterChain.doFilter(request, response);
	}

}
