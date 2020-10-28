package org.springframework.boot.web.reactive.context;

import org.springframework.boot.web.server.WebServer;
import org.springframework.context.SmartLifecycle;

/**
 * {@link SmartLifecycle} to start and stop the {@link WebServer} in a
 * {@link ReactiveWebServerApplicationContext}.
 *

 */
class WebServerStartStopLifecycle implements SmartLifecycle {

	private final WebServerManager weServerManager;

	private volatile boolean running;

	WebServerStartStopLifecycle(WebServerManager weServerManager) {
		this.weServerManager = weServerManager;
	}

	@Override
	public void start() {
		this.weServerManager.start();
		this.running = true;
	}

	@Override
	public void stop() {
		this.running = false;
		this.weServerManager.stop();
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
