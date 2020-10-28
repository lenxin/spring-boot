package org.springframework.boot.autoconfigure.elasticsearch;

import java.time.Duration;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * Configuration properties for Elasticsearch REST clients.
 *

 * @since 2.1.0
 */
@ConfigurationProperties(prefix = "spring.elasticsearch.rest")
public class ElasticsearchRestClientProperties {

	/**
	 * Comma-separated list of the Elasticsearch instances to use.
	 */
	private List<String> uris = new ArrayList<>(Collections.singletonList("http://localhost:9200"));

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
	private Duration connectionTimeout = Duration.ofSeconds(1);

	/**
	 * Read timeout.
	 */
	private Duration readTimeout = Duration.ofSeconds(30);

	public List<String> getUris() {
		return this.uris;
	}

	public void setUris(List<String> uris) {
		this.uris = uris;
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

	public Duration getReadTimeout() {
		return this.readTimeout;
	}

	public void setReadTimeout(Duration readTimeout) {
		this.readTimeout = readTimeout;
	}

}
