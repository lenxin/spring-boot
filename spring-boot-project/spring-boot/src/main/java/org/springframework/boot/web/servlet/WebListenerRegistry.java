package org.springframework.boot.web.servlet;

import javax.servlet.annotation.WebListener;

/**
 * A registry that holds {@link WebListener @WebListeners}.
 *

 * @since 2.4.0
 */
public interface WebListenerRegistry {

	/**
	 * Adds web listeners that will be registered with the servlet container.
	 * @param webListenerClassNames the class names of the web listeners
	 */
	void addWebListeners(String... webListenerClassNames);

}
