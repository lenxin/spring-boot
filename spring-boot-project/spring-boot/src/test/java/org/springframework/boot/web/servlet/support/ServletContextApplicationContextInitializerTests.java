package org.springframework.boot.web.servlet.support;

import javax.servlet.ServletContext;

import org.junit.jupiter.api.Test;

import org.springframework.web.context.ConfigurableWebApplicationContext;
import org.springframework.web.context.WebApplicationContext;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

/**
 * Tests for {@link ServletContextApplicationContextInitializer}.
 *

 */
class ServletContextApplicationContextInitializerTests {

	private final ServletContext servletContext = mock(ServletContext.class);

	private final ConfigurableWebApplicationContext applicationContext = mock(ConfigurableWebApplicationContext.class);

	@Test
	void servletContextIsSetOnTheApplicationContext() {
		new ServletContextApplicationContextInitializer(this.servletContext).initialize(this.applicationContext);
		verify(this.applicationContext).setServletContext(this.servletContext);
	}

	@Test
	void applicationContextIsNotStoredInServletContextByDefault() {
		new ServletContextApplicationContextInitializer(this.servletContext).initialize(this.applicationContext);
		verify(this.servletContext, never()).setAttribute(WebApplicationContext.ROOT_WEB_APPLICATION_CONTEXT_ATTRIBUTE,
				this.applicationContext);
	}

	@Test
	void applicationContextCanBeStoredInServletContext() {
		new ServletContextApplicationContextInitializer(this.servletContext, true).initialize(this.applicationContext);
		verify(this.servletContext).setAttribute(WebApplicationContext.ROOT_WEB_APPLICATION_CONTEXT_ATTRIBUTE,
				this.applicationContext);
	}

}
