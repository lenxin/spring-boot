package org.springframework.boot.web.embedded.tomcat;

import org.apache.catalina.Context;

/**
 * Callback interface that can be used to customize a Tomcat {@link Context}.
 *

 * @see TomcatServletWebServerFactory
 * @since 2.0.0
 */
@FunctionalInterface
public interface TomcatContextCustomizer {

	/**
	 * Customize the context.
	 * @param context the context to customize
	 */
	void customize(Context context);

}
