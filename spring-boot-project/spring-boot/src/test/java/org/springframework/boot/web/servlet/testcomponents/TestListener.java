package org.springframework.boot.web.servlet.testcomponents;

import java.io.IOException;
import java.util.EnumSet;

import javax.servlet.DispatcherType;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebListener;

@WebListener
public class TestListener implements ServletContextListener {

	@Override
	public void contextInitialized(ServletContextEvent sce) {
		sce.getServletContext().addFilter("listenerAddedFilter", new ListenerAddedFilter())
				.addMappingForUrlPatterns(EnumSet.of(DispatcherType.REQUEST), true, "/*");
		sce.getServletContext().setAttribute("listenerAttribute", "alpha");
	}

	@Override
	public void contextDestroyed(ServletContextEvent sce) {

	}

	static class ListenerAddedFilter implements Filter {

		@Override
		public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
				throws IOException, ServletException {
			request.setAttribute("listenerAddedFilterAttribute", "charlie");
			chain.doFilter(request, response);
		}

	}

}
