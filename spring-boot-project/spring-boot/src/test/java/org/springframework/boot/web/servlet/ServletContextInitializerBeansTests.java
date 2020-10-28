package org.springframework.boot.web.servlet;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServlet;

import org.junit.jupiter.api.Test;

import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Tests for {@link ServletContextInitializerBeans}.
 *

 */
class ServletContextInitializerBeansTests {

	private ConfigurableApplicationContext context;

	@Test
	void servletThatImplementsServletContextInitializerIsOnlyRegisteredOnce() {
		load(ServletConfiguration.class);
		ServletContextInitializerBeans initializerBeans = new ServletContextInitializerBeans(
				this.context.getBeanFactory());
		assertThat(initializerBeans.size()).isEqualTo(1);
		assertThat(initializerBeans.iterator()).toIterable().hasOnlyElementsOfType(TestServlet.class);
	}

	@Test
	void filterThatImplementsServletContextInitializerIsOnlyRegisteredOnce() {
		load(FilterConfiguration.class);
		ServletContextInitializerBeans initializerBeans = new ServletContextInitializerBeans(
				this.context.getBeanFactory());
		assertThat(initializerBeans.size()).isEqualTo(1);
		assertThat(initializerBeans.iterator()).toIterable().hasOnlyElementsOfType(TestFilter.class);
	}

	@Test
	void looksForInitializerBeansOfSpecifiedType() {
		load(TestConfiguration.class);
		ServletContextInitializerBeans initializerBeans = new ServletContextInitializerBeans(
				this.context.getBeanFactory(), TestServletContextInitializer.class);
		assertThat(initializerBeans.size()).isEqualTo(1);
		assertThat(initializerBeans.iterator()).toIterable().hasOnlyElementsOfType(TestServletContextInitializer.class);
	}

	private void load(Class<?>... configuration) {
		this.context = new AnnotationConfigApplicationContext(configuration);
	}

	@Configuration(proxyBeanMethods = false)
	static class ServletConfiguration {

		@Bean
		TestServlet testServlet() {
			return new TestServlet();
		}

	}

	@Configuration(proxyBeanMethods = false)
	static class FilterConfiguration {

		@Bean
		TestFilter testFilter() {
			return new TestFilter();
		}

	}

	@Configuration(proxyBeanMethods = false)
	static class TestConfiguration {

		@Bean
		TestServletContextInitializer testServletContextInitializer() {
			return new TestServletContextInitializer();
		}

		@Bean
		OtherTestServletContextInitializer otherTestServletContextInitializer() {
			return new OtherTestServletContextInitializer();
		}

	}

	static class TestServlet extends HttpServlet implements ServletContextInitializer {

		@Override
		public void onStartup(ServletContext servletContext) {

		}

	}

	static class TestFilter implements Filter, ServletContextInitializer {

		@Override
		public void onStartup(ServletContext servletContext) {

		}

		@Override
		public void init(FilterConfig filterConfig) {

		}

		@Override
		public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) {

		}

		@Override
		public void destroy() {

		}

	}

	static class TestServletContextInitializer implements ServletContextInitializer {

		@Override
		public void onStartup(ServletContext servletContext) throws ServletException {

		}

	}

	static class OtherTestServletContextInitializer implements ServletContextInitializer {

		@Override
		public void onStartup(ServletContext servletContext) throws ServletException {

		}

	}

}
