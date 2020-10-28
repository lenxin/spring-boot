package org.springframework.boot.web.servlet;

import javax.servlet.Filter;

import org.springframework.boot.testsupport.classpath.ClassPathExclusions;
import org.springframework.boot.web.servlet.mock.MockFilter;

import static org.mockito.ArgumentMatchers.eq;

/**
 * Tests for {@link FilterRegistrationBean} when {@code spring-web} is not on the
 * classpath.
 *

 */
@ClassPathExclusions("spring-web-*.jar")
public class NoSpringWebFilterRegistrationBeanTests extends AbstractFilterRegistrationBeanTests {

	private final MockFilter filter = new MockFilter();

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
