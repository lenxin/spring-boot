package org.springframework.boot.web.servlet;

import java.util.EventListener;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextListener;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

/**
 * Tests for {@link ServletListenerRegistrationBean}.
 *

 */
@ExtendWith(MockitoExtension.class)
class ServletListenerRegistrationBeanTests {

	@Mock
	private ServletContextListener listener;

	@Mock
	private ServletContext servletContext;

	@Test
	void startupWithDefaults() throws Exception {
		ServletListenerRegistrationBean<ServletContextListener> bean = new ServletListenerRegistrationBean<>(
				this.listener);
		bean.onStartup(this.servletContext);
		verify(this.servletContext).addListener(this.listener);
	}

	@Test
	void disable() throws Exception {
		ServletListenerRegistrationBean<ServletContextListener> bean = new ServletListenerRegistrationBean<>(
				this.listener);
		bean.setEnabled(false);
		bean.onStartup(this.servletContext);
		verify(this.servletContext, never()).addListener(any(ServletContextListener.class));
	}

	@Test
	void cannotRegisterUnsupportedType() {
		assertThatIllegalArgumentException()
				.isThrownBy(() -> new ServletListenerRegistrationBean<>(new EventListener() {

				})).withMessageContaining("Listener is not of a supported type");
	}

}
