package org.springframework.boot.web.servlet.context;

import org.springframework.boot.web.server.WebServer;
import org.springframework.context.SmartLifecycle;

/**
 * {@link SmartLifecycle} to trigger {@link WebServer} graceful shutdown in a
 * {@link ServletWebServerApplicationContext}.
 *

 */
class WebServerGracefulShutdownLifecycle implements SmartLifecycle {

	private final WebServer webServer;

	private volatile boolean running;

	WebServerGracefulShutdownLifecycle(WebServer webServer) {
		this.webServer = webServer;
	}

	@Override
	public void start() {
		this.running = true;
	}

	@Override
	public void stop() {
		throw new UnsupportedOperationException("Stop must not be invoked directly");
	}

	@Override
	public void stop(Runnable callback) {
		this.running = false;
		this.webServer.shutDownGracefully((result) -> callback.run());
	}

	@Override
	public boolean isRunning() {
		return this.running;
	}

}
