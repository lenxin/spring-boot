package org.springframework.boot.test.util;

import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;

/**
 * Application context related test utilities.
 *

 * @since 1.4.0
 */
public abstract class ApplicationContextTestUtils {

	/**
	 * Closes this {@link ApplicationContext} and its parent hierarchy if any.
	 * @param context the context to close (can be {@code null})
	 */
	public static void closeAll(ApplicationContext context) {
		if (context != null) {
			if (context instanceof ConfigurableApplicationContext) {
				((ConfigurableApplicationContext) context).close();
			}
			closeAll(context.getParent());
		}
	}

}
