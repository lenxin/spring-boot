package org.springframework.boot.devtools.restart;

import java.net.URL;

/**
 * Strategy interface used to initialize a {@link Restarter}.
 *

 * @since 1.3.0
 * @see DefaultRestartInitializer
 */
@FunctionalInterface
public interface RestartInitializer {

	/**
	 * {@link RestartInitializer} that doesn't return any URLs.
	 */
	RestartInitializer NONE = (thread) -> null;

	/**
	 * Return the initial set of URLs for the {@link Restarter} or {@code null} if no
	 * initial restart is required.
	 * @param thread the source thread
	 * @return initial URLs or {@code null}
	 */
	URL[] getInitialUrls(Thread thread);

}
