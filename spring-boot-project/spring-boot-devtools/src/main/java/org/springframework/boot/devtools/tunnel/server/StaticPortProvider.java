package org.springframework.boot.devtools.tunnel.server;

import org.springframework.util.Assert;

/**
 * {@link PortProvider} for a static port that won't change.
 *

 * @since 1.3.0
 */
public class StaticPortProvider implements PortProvider {

	private final int port;

	public StaticPortProvider(int port) {
		Assert.isTrue(port > 0, "Port must be positive");
		this.port = port;
	}

	@Override
	public int getPort() {
		return this.port;
	}

}
