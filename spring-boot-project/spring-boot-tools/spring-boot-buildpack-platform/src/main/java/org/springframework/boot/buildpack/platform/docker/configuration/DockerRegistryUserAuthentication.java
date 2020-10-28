package org.springframework.boot.buildpack.platform.docker.configuration;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Docker registry authentication configuration using user credentials.
 *

 */
class DockerRegistryUserAuthentication extends JsonEncodedDockerRegistryAuthentication {

	@JsonProperty
	private final String username;

	@JsonProperty
	private final String password;

	@JsonProperty("serveraddress")
	private final String url;

	@JsonProperty
	private final String email;

	DockerRegistryUserAuthentication(String username, String password, String url, String email) {
		this.username = username;
		this.password = password;
		this.url = url;
		this.email = email;
		createAuthHeader();
	}

	String getUsername() {
		return this.username;
	}

	String getPassword() {
		return this.password;
	}

	String getUrl() {
		return this.url;
	}

	String getEmail() {
		return this.email;
	}

}
