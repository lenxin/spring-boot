package org.springframework.boot.autoconfigure.data.elasticsearch;

import java.time.Duration;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.util.unit.DataSize;

/**
 * Configuration properties for Elasticsearch Reactive REST clients.
 *

 * @since 2.2.0
 */
@ConfigurationProperties(prefix = "spring.data.elasticsearch.client.reactive")
public class ReactiveElasticsearchRestClientProperties {

	/**
	 * Comma-separated list of the Elasticsearch endpoints to connect to.
	 */
	private List<String> endpoints = new ArrayList<>(Collections.singletonList("localhost:9200"));

	/**
	 * Whether the client should use SSL to connect to the endpoints.
	 */
	private boolean useSsl = false;

	/**
	 * Credentials username.
	 */
	private String username;

	/**
	 * Credentials password.
	 */
	private String password;

	/**
	 * Connection timeout.
	 */
	private Duration connectionTimeout;

	/**
	 * Read and Write Socket timeout.
	 */
	private Duration socketTimeout;

	/**
	 * Limit on the number of bytes that can be buffered whenever the input stream needs
	 * to be aggregated.
	 */
	private DataSize maxInMemorySize;

	public List<String> getEndpoints() {
		return this.endpoints;
	}

	public void setEndpoints(List<String> endpoints) {
		this.endpoints = endpoints;
	}

	public boolean isUseSsl() {
		return this.useSsl;
	}

	public void setUseSsl(boolean useSsl) {
		this.useSsl = useSsl;
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

	public Duration getConnectionTimeout() {
		return this.connectionTimeout;
	}

	public void setConnectionTimeout(Duration connectionTimeout) {
		this.connectionTimeout = connectionTimeout;
	}

	public Duration getSocketTimeout() {
		return this.socketTimeout;
	}

	public void setSocketTimeout(Duration socketTimeout) {
		this.socketTimeout = socketTimeout;
	}

	public DataSize getMaxInMemorySize() {
		return this.maxInMemorySize;
	}

	public void setMaxInMemorySize(DataSize maxInMemorySize) {
		this.maxInMemorySize = maxInMemorySize;
	}

}
