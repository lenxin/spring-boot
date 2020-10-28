package org.springframework.boot.rsocket.context;

import io.rsocket.SocketAcceptor;

import org.springframework.boot.rsocket.server.RSocketServer;
import org.springframework.boot.rsocket.server.RSocketServerFactory;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.ApplicationEventPublisherAware;
import org.springframework.context.SmartLifecycle;
import org.springframework.util.Assert;

/**
 * Bootstrap an {@link RSocketServer} and start it with the application context.
 *

 * @since 2.2.0
 */
public class RSocketServerBootstrap implements ApplicationEventPublisherAware, SmartLifecycle {

	private final RSocketServer server;

	private ApplicationEventPublisher eventPublisher;

	public RSocketServerBootstrap(RSocketServerFactory serverFactory, SocketAcceptor socketAcceptor) {
		Assert.notNull(serverFactory, "ServerFactory must not be null");
		this.server = serverFactory.create(socketAcceptor);
	}

	@Override
	public void setApplicationEventPublisher(ApplicationEventPublisher applicationEventPublisher) {
		this.eventPublisher = applicationEventPublisher;
	}

	@Override
	public void start() {
		this.server.start();
		this.eventPublisher.publishEvent(new RSocketServerInitializedEvent(this.server));
	}

	@Override
	public void stop() {
		this.server.stop();
	}

	@Override
	public boolean isRunning() {
		RSocketServer server = this.server;
		if (server != null) {
			return server.address() != null;
		}
		return false;
	}

}
