package org.springframework.boot.autoconfigure.jms;

import java.time.Duration;

/**
 * Configuration properties for connection factory pooling.
 *

 * @since 2.1.0
 */
public class JmsPoolConnectionFactoryProperties {

	/**
	 * Whether a JmsPoolConnectionFactory should be created, instead of a regular
	 * ConnectionFactory.
	 */
	private boolean enabled;

	/**
	 * Whether to block when a connection is requested and the pool is full. Set it to
	 * false to throw a "JMSException" instead.
	 */
	private boolean blockIfFull = true;

	/**
	 * Blocking period before throwing an exception if the pool is still full.
	 */
	private Duration blockIfFullTimeout = Duration.ofMillis(-1);

	/**
	 * Connection idle timeout.
	 */
	private Duration idleTimeout = Duration.ofSeconds(30);

	/**
	 * Maximum number of pooled connections.
	 */
	private int maxConnections = 1;

	/**
	 * Maximum number of pooled sessions per connection in the pool.
	 */
	private int maxSessionsPerConnection = 500;

	/**
	 * Time to sleep between runs of the idle connection eviction thread. When negative,
	 * no idle connection eviction thread runs.
	 */
	private Duration timeBetweenExpirationCheck = Duration.ofMillis(-1);

	/**
	 * Whether to use only one anonymous "MessageProducer" instance. Set it to false to
	 * create one "MessageProducer" every time one is required.
	 */
	private boolean useAnonymousProducers = true;

	public boolean isEnabled() {
		return this.enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	public boolean isBlockIfFull() {
		return this.blockIfFull;
	}

	public void setBlockIfFull(boolean blockIfFull) {
		this.blockIfFull = blockIfFull;
	}

	public Duration getBlockIfFullTimeout() {
		return this.blockIfFullTimeout;
	}

	public void setBlockIfFullTimeout(Duration blockIfFullTimeout) {
		this.blockIfFullTimeout = blockIfFullTimeout;
	}

	public Duration getIdleTimeout() {
		return this.idleTimeout;
	}

	public void setIdleTimeout(Duration idleTimeout) {
		this.idleTimeout = idleTimeout;
	}

	public int getMaxConnections() {
		return this.maxConnections;
	}

	public void setMaxConnections(int maxConnections) {
		this.maxConnections = maxConnections;
	}

	public int getMaxSessionsPerConnection() {
		return this.maxSessionsPerConnection;
	}

	public void setMaxSessionsPerConnection(int maxSessionsPerConnection) {
		this.maxSessionsPerConnection = maxSessionsPerConnection;
	}

	public Duration getTimeBetweenExpirationCheck() {
		return this.timeBetweenExpirationCheck;
	}

	public void setTimeBetweenExpirationCheck(Duration timeBetweenExpirationCheck) {
		this.timeBetweenExpirationCheck = timeBetweenExpirationCheck;
	}

	public boolean isUseAnonymousProducers() {
		return this.useAnonymousProducers;
	}

	public void setUseAnonymousProducers(boolean useAnonymousProducers) {
		this.useAnonymousProducers = useAnonymousProducers;
	}

}
