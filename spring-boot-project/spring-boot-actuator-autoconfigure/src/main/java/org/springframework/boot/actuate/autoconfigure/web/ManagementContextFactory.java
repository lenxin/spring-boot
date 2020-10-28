package org.springframework.boot.actuate.autoconfigure.web;

import org.springframework.boot.web.context.ConfigurableWebServerApplicationContext;
import org.springframework.context.ApplicationContext;

/**
 * Factory for creating a separate management context when the management web server is
 * running on a different port to the main application.
 *

 * @since 2.0.0
 */
@FunctionalInterface
public interface ManagementContextFactory {

	/**
	 * Create the management application context.
	 * @param parent the parent context
	 * @param configurationClasses the configuration classes
	 * @return a configured application context
	 */
	ConfigurableWebServerApplicationContext createManagementContext(ApplicationContext parent,
			Class<?>... configurationClasses);

}
