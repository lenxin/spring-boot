package org.springframework.boot.autoconfigure.web.servlet;

import java.util.Collections;

import org.junit.jupiter.api.Test;

import org.springframework.web.servlet.DispatcherServlet;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;

/**
 * Tests for {@link DispatcherServletRegistrationBean}.
 *

 */
class DispatcherServletRegistrationBeanTests {

	@Test
	void createWhenPathIsNullThrowsException() {
		assertThatIllegalArgumentException()
				.isThrownBy(() -> new DispatcherServletRegistrationBean(new DispatcherServlet(), null))
				.withMessageContaining("Path must not be null");
	}

	@Test
	void getPathReturnsPath() {
		DispatcherServletRegistrationBean bean = new DispatcherServletRegistrationBean(new DispatcherServlet(),
				"/test");
		assertThat(bean.getPath()).isEqualTo("/test");
	}

	@Test
	void getUrlMappingsReturnsSinglePathMappedPattern() {
		DispatcherServletRegistrationBean bean = new DispatcherServletRegistrationBean(new DispatcherServlet(),
				"/test");
		assertThat(bean.getUrlMappings()).containsOnly("/test/*");
	}

	@Test
	void setUrlMappingsCannotBeCalled() {
		DispatcherServletRegistrationBean bean = new DispatcherServletRegistrationBean(new DispatcherServlet(),
				"/test");
		assertThatExceptionOfType(UnsupportedOperationException.class)
				.isThrownBy(() -> bean.setUrlMappings(Collections.emptyList()));
	}

	@Test
	void addUrlMappingsCannotBeCalled() {
		DispatcherServletRegistrationBean bean = new DispatcherServletRegistrationBean(new DispatcherServlet(),
				"/test");
		assertThatExceptionOfType(UnsupportedOperationException.class).isThrownBy(() -> bean.addUrlMappings("/test"));
	}

}
