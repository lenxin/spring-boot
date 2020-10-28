package org.springframework.boot.test.autoconfigure.web.servlet.mockmvc;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletResponse;

import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.core.Ordered;
import org.springframework.stereotype.Component;

/**
 * Example filter used with {@link WebMvcTest @WebMvcTest} tests.
 *

 */
@Component
public class ExampleFilter implements Filter, Ordered {

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		chain.doFilter(request, response);
		((HttpServletResponse) response).addHeader("x-test", "abc");
	}

	@Override
	public void destroy() {
	}

	@Override
	public int getOrder() {
		return SecurityProperties.DEFAULT_FILTER_ORDER - 1;
	}

}
