package org.springframework.boot.web.servlet;

import javax.servlet.annotation.WebListener;

/**
 * Interface to be implemented by types that register {@link WebListener @WebListeners}.
 *

 * @since 2.4.0
 */
public interface WebListenerRegistrar {

	/**
	 * Register web listeners with the given registry.
	 * @param registry the web listener registry
	 */
	void register(WebListenerRegistry registry);

}
