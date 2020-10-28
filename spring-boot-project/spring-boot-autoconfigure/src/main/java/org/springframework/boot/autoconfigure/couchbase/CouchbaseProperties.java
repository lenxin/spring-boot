package org.springframework.boot.autoconfigure.couchbase;

import java.time.Duration;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.util.StringUtils;

/**
 * Configuration properties for Couchbase.
 *





 * @since 1.4.0
 */
@ConfigurationProperties(prefix = "spring.couchbase")
public class CouchbaseProperties {

	/**
	 * Connection string used to locate the Couchbase cluster.
	 */
	private String connectionString;

	/**
	 * Cluster username.
	 */
	private String username;

	/**
	 * Cluster password.
	 */
	private String password;

	private final Env env = new Env();

	public String getConnectionString() {
		return this.connectionString;
	}

	public void setConnectionString(String connectionString) {
		this.connectionString = connectionString;
	}

	public String getUsername() {
		return this.username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return this.password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Env getEnv() {
		return this.env;
	}

	public static class Env {

		private final Io io = new Io();

		private final Ssl ssl = new Ssl();

		private final Timeouts timeouts = new Timeouts();

		public Io getIo() {
			return this.io;
		}

		public Ssl getSsl() {
			return this.ssl;
		}

		public Timeouts getTimeouts() {
			return this.timeouts;
		}

	}

	public static class Io {

		/**
		 * Minimum number of sockets per node.
		 */
		private int minEndpoints = 1;

		/**
		 * Maximum number of sockets per node.
		 */
		private int maxEndpoints = 12;

		/**
		 * Length of time an HTTP connection may remain idle before it is closed and
		 * removed from the pool.
		 */
		private Duration idleHttpConnectionTimeout = Duration.ofMillis(4500);

		public int getMinEndpoints() {
			return this.minEndpoints;
		}

		public void setMinEndpoints(int minEndpoints) {
			this.minEndpoints = minEndpoints;
		}

		public int getMaxEndpoints() {
			return this.maxEndpoints;
		}

		public void setMaxEndpoints(int maxEndpoints) {
			this.maxEndpoints = maxEndpoints;
		}

		public Duration getIdleHttpConnectionTimeout() {
			return this.idleHttpConnectionTimeout;
		}

		public void setIdleHttpConnectionTimeout(Duration idleHttpConnectionTimeout) {
			this.idleHttpConnectionTimeout = idleHttpConnectionTimeout;
		}

	}

	public static class Ssl {

		/**
		 * Whether to enable SSL support. Enabled automatically if a "keyStore" is
		 * provided unless specified otherwise.
		 */
		private Boolean enabled;

		/**
		 * Path to the JVM key store that holds the certificates.
		 */
		private String keyStore;

		/**
		 * Password used to access the key store.
		 */
		private String keyStorePassword;

		public Boolean getEnabled() {
			return (this.enabled != null) ? this.enabled : StringUtils.hasText(this.keyStore);
		}

		public void setEnabled(Boolean enabled) {
			this.enabled = enabled;
		}

		public String getKeyStore() {
			return this.keyStore;
		}

		public void setKeyStore(String keyStore) {
			this.keyStore = keyStore;
		}

		public String getKeyStorePassword() {
			return this.keyStorePassword;
		}

		public void setKeyStorePassword(String keyStorePassword) {
			this.keyStorePassword = keyStorePassword;
		}

	}

	public static class Timeouts {

		/**
		 * Bucket connect timeout.
		 */
		private Duration connect = Duration.ofSeconds(10);

		/**
		 * Bucket disconnect timeout.
		 */
		private Duration disconnect = Duration.ofSeconds(10);

		/**
		 * Timeout for operations on a specific key-value.
		 */
		private Duration keyValue = Duration.ofMillis(2500);

		/**
		 * Timeout for operations on a specific key-value with a durability level.
		 */
		private Duration keyValueDurable = Duration.ofSeconds(10);

		/**
		 * N1QL query operations timeout.
		 */
		private Duration query = Duration.ofSeconds(75);

		/**
		 * Regular and geospatial view operations timeout.
		 */
		private Duration view = Duration.ofSeconds(75);

		/**
		 * Timeout for the search service.
		 */
		private Duration search = Duration.ofSeconds(75);

		/**
		 * Timeout for the analytics service.
		 */
		private Duration analytics = Duration.ofSeconds(75);

		/**
		 * Timeout for the management operations.
		 */
		private Duration management = Duration.ofSeconds(75);

		public Duration getConnect() {
			return this.connect;
		}

		public void setConnect(Duration connect) {
			this.connect = connect;
		}

		public Duration getDisconnect() {
			return this.disconnect;
		}

		public void setDisconnect(Duration disconnect) {
			this.disconnect = disconnect;
		}

		public Duration getKeyValue() {
			return this.keyValue;
		}

		public void setKeyValue(Duration keyValue) {
			this.keyValue = keyValue;
		}

		public Duration getKeyValueDurable() {
			return this.keyValueDurable;
		}

		public void setKeyValueDurable(Duration keyValueDurable) {
			this.keyValueDurable = keyValueDurable;
		}

		public Duration getQuery() {
			return this.query;
		}

		public void setQuery(Duration query) {
			this.query = query;
		}

		public Duration getView() {
			return this.view;
		}

		public void setView(Duration view) {
			this.view = view;
		}

		public Duration getSearch() {
			return this.search;
		}

		public void setSearch(Duration search) {
			this.search = search;
		}

		public Duration getAnalytics() {
			return this.analytics;
		}

		public void setAnalytics(Duration analytics) {
			this.analytics = analytics;
		}

		public Duration getManagement() {
			return this.management;
		}

		public void setManagement(Duration management) {
			this.management = management;
		}

	}

}
