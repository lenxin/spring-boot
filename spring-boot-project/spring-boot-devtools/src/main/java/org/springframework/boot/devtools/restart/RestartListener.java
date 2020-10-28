package org.springframework.boot.devtools.restart;

/**
 * Listener that is notified of application restarts.
 *

 * @since 1.3.0
 */
@FunctionalInterface
public interface RestartListener {

	/**
	 * Called before an application restart.
	 */
	void beforeRestart();

}
