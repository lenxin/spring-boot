package org.springframework.boot.web.reactive.context;

import org.springframework.boot.web.server.WebServer;
import org.springframework.context.SmartLifecycle;

/**
 * {@link SmartLifecycle} to trigger {@link WebServer} graceful shutdown in a
 * {@link ReactiveWebServerApplicationContext}.
 *

 */
class WebServerGracefulShutdownLifecycle implements SmartLifecycle {

	private final WebServerManager serverManager;

	private volatile boolean running;

	WebServerGracefulShutdownLifecycle(WebServerManager serverManager) {
		this.serverManager = serverManager;
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
		this.serverManager.shutDownGracefully(callback);
	}

	@Override
	public boolean isRunning() {
		return this.running;
	}

}
