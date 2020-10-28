package org.springframework.boot.web.servlet.context;

import org.springframework.boot.web.server.WebServer;
import org.springframework.context.SmartLifecycle;

/**
 * {@link SmartLifecycle} to start and stop the {@link WebServer} in a
 * {@link ServletWebServerApplicationContext}.
 *

 */
class WebServerStartStopLifecycle implements SmartLifecycle {

	private final ServletWebServerApplicationContext applicationContext;

	private final WebServer webServer;

	private volatile boolean running;

	WebServerStartStopLifecycle(ServletWebServerApplicationContext applicationContext, WebServer webServer) {
		this.applicationContext = applicationContext;
		this.webServer = webServer;
	}

	@Override
	public void start() {
		this.webServer.start();
		this.running = true;
		this.applicationContext
				.publishEvent(new ServletWebServerInitializedEvent(this.webServer, this.applicationContext));
	}

	@Override
	public void stop() {
		this.webServer.stop();
	}

	@Override
	public boolean isRunning() {
		return this.running;
	}

	@Override
	public int getPhase() {
		return Integer.MAX_VALUE - 1;
	}

}
