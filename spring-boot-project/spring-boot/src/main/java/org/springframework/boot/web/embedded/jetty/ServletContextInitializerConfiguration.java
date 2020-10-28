package org.springframework.boot.web.embedded.jetty;

import javax.servlet.ServletException;

import org.eclipse.jetty.webapp.AbstractConfiguration;
import org.eclipse.jetty.webapp.Configuration;
import org.eclipse.jetty.webapp.WebAppContext;

import org.springframework.boot.web.servlet.ServletContextInitializer;
import org.springframework.util.Assert;

/**
 * Jetty {@link Configuration} that calls {@link ServletContextInitializer}s.
 *


 * @since 2.0.0
 */
public class ServletContextInitializerConfiguration extends AbstractConfiguration {

	private final ServletContextInitializer[] initializers;

	/**
	 * Create a new {@link ServletContextInitializerConfiguration}.
	 * @param initializers the initializers that should be invoked
	 * @since 1.2.1
	 */
	public ServletContextInitializerConfiguration(ServletContextInitializer... initializers) {
		Assert.notNull(initializers, "Initializers must not be null");
		this.initializers = initializers;
	}

	@Override
	public void configure(WebAppContext context) throws Exception {
		ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
		Thread.currentThread().setContextClassLoader(context.getClassLoader());
		try {
			callInitializers(context);
		}
		finally {
			Thread.currentThread().setContextClassLoader(classLoader);
		}
	}

	private void callInitializers(WebAppContext context) throws ServletException {
		try {
			setExtendedListenerTypes(context, true);
			for (ServletContextInitializer initializer : this.initializers) {
				initializer.onStartup(context.getServletContext());
			}
		}
		finally {
			setExtendedListenerTypes(context, false);
		}
	}

	private void setExtendedListenerTypes(WebAppContext context, boolean extended) {
		try {
			context.getServletContext().setExtendedListenerTypes(extended);
		}
		catch (NoSuchMethodError ex) {
			// Not available on Jetty 8
		}
	}

}
