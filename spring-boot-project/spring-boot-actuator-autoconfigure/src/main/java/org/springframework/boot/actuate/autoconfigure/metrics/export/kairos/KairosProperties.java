package org.springframework.boot.actuate.autoconfigure.metrics.export.kairos;

import org.springframework.boot.actuate.autoconfigure.metrics.export.properties.StepRegistryProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * {@link ConfigurationProperties @ConfigurationProperties} for configuring KairosDB
 * metrics export.
 *

 * @since 2.1.0
 */
@ConfigurationProperties(prefix = "management.metrics.export.kairos")
public class KairosProperties extends StepRegistryProperties {

	/**
	 * URI of the KairosDB server.
	 */
	private String uri = "http://localhost:8080/api/v1/datapoints";

	/**
	 * Login user of the KairosDB server.
	 */
	private String userName;

	/**
	 * Login password of the KairosDB server.
	 */
	private String password;

	public String getUri() {
		return this.uri;
	}

	public void setUri(String uri) {
		this.uri = uri;
	}

	public String getUserName() {
		return this.userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return this.password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

}
