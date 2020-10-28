package org.springframework.boot.test.autoconfigure.web.servlet.mockmvc;

import java.io.IOException;
import java.security.Principal;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.core.Ordered;

/**
 * {@link Filter} that is ordered to run after Spring Security's filter.
 *

 */
public class AfterSecurityFilter implements Filter, Ordered {

	@Override
	public int getOrder() {
		return SecurityProperties.DEFAULT_FILTER_ORDER + 1;
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		Principal principal = ((HttpServletRequest) request).getUserPrincipal();
		if (principal == null) {
			throw new ServletException("No user principal");
		}
		response.getWriter().write(principal.getName());
		response.getWriter().flush();
	}

}
