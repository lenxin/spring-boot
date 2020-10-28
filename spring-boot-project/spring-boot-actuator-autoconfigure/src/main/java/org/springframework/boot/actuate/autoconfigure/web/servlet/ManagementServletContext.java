package org.springframework.boot.actuate.autoconfigure.web.servlet;

/**
 * Provides information about the management servlet context for MVC controllers to use.
 *


 * @since 2.0.0
 */
@FunctionalInterface
public interface ManagementServletContext {

	/**
	 * Return the servlet path of the management server.
	 * @return the servlet path
	 */
	String getServletPath();

}
