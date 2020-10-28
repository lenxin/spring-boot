package org.springframework.boot.web.servlet.error;

import org.springframework.stereotype.Controller;

/**
 * Marker interface used to identify a {@link Controller @Controller} that should be used
 * to render errors.
 *


 * @since 2.0.0
 */
@FunctionalInterface
public interface ErrorController {

	/**
	 * The return value from this method is not used; the property `server.error.path`
	 * must be set to override the default error page path.
	 * @return the error path
	 * @deprecated since 2.3.0 in favor of setting the property `server.error.path`
	 */
	@Deprecated
	String getErrorPath();

}
