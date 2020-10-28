package org.springframework.boot.web.servlet.context;

import org.springframework.boot.web.context.WebServerInitializedEvent;
import org.springframework.boot.web.server.WebServer;

/**
 * Event to be published after the {@link WebServer} is ready. Useful for obtaining the
 * local port of a running server.
 *
 * <p>
 * Normally it will have been started, but listeners are free to inspect the server and
 * stop and start it if they want to.
 *

 * @since 2.0.0
 */
@SuppressWarnings("serial")
public class ServletWebServerInitializedEvent extends WebServerInitializedEvent {

	private final ServletWebServerApplicationContext applicationContext;

	public ServletWebServerInitializedEvent(WebServer webServer,
			ServletWebServerApplicationContext applicationContext) {
		super(webServer);
		this.applicationContext = applicationContext;
	}

	/**
	 * Access the application context that the server was created in. Sometimes it is
	 * prudent to check that this matches expectations (like being equal to the current
	 * context) before acting on the server itself.
	 * @return the applicationContext that the server was created from
	 */
	@Override
	public ServletWebServerApplicationContext getApplicationContext() {
		return this.applicationContext;
	}

}
