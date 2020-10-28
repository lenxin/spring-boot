package org.springframework.boot.web.servlet;

import java.io.IOException;
import java.util.EnumSet;

import javax.servlet.DispatcherType;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.junit.jupiter.api.Test;

import org.springframework.boot.web.servlet.mock.MockFilter;
import org.springframework.web.filter.OncePerRequestFilter;

import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

/**
 * Tests for {@link FilterRegistrationBean}.
 *

 */
class FilterRegistrationBeanTests extends AbstractFilterRegistrationBeanTests {

	private final MockFilter filter = new MockFilter();

	private final OncePerRequestFilter oncePerRequestFilter = new OncePerRequestFilter() {

		@Override
		protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
				FilterChain filterChain) throws ServletException, IOException {
			filterChain.doFilter(request, response);
		}

	};

	@Test
	void setFilter() throws Exception {
		FilterRegistrationBean<Filter> bean = new FilterRegistrationBean<>();
		bean.setFilter(this.filter);
		bean.onStartup(this.servletContext);
		verify(this.servletContext).addFilter("mockFilter", this.filter);
	}

	@Test
	void setFilterMustNotBeNull() throws Exception {
		FilterRegistrationBean<Filter> bean = new FilterRegistrationBean<>();
		assertThatIllegalArgumentException().isThrownBy(() -> bean.onStartup(this.servletContext))
				.withMessageContaining("Filter must not be null");
	}

	@Test
	void constructFilterMustNotBeNull() {
		assertThatIllegalArgumentException().isThrownBy(() -> new FilterRegistrationBean<>(null))
				.withMessageContaining("Filter must not be null");
	}

	@Test
	void createServletRegistrationBeanMustNotBeNull() {
		assertThatIllegalArgumentException()
				.isThrownBy(() -> new FilterRegistrationBean<>(this.filter, (ServletRegistrationBean[]) null))
				.withMessageContaining("ServletRegistrationBeans must not be null");
	}

	@Test
	void startupWithOncePerRequestDefaults() throws Exception {
		given(this.servletContext.addFilter(anyString(), any(Filter.class))).willReturn(this.registration);
		FilterRegistrationBean<?> bean = new FilterRegistrationBean<>(this.oncePerRequestFilter);
		bean.onStartup(this.servletContext);
		verify(this.servletContext).addFilter(eq("oncePerRequestFilter"), eq(this.oncePerRequestFilter));
		verify(this.registration).setAsyncSupported(true);
		verify(this.registration).addMappingForUrlPatterns(EnumSet.allOf(DispatcherType.class), false, "/*");
	}

	@Override
	protected AbstractFilterRegistrationBean<MockFilter> createFilterRegistrationBean(
			ServletRegistrationBean<?>... servletRegistrationBeans) {
		return new FilterRegistrationBean<>(this.filter, servletRegistrationBeans);
	}

	@Override
	protected Filter getExpectedFilter() {
		return eq(this.filter);
	}

}
