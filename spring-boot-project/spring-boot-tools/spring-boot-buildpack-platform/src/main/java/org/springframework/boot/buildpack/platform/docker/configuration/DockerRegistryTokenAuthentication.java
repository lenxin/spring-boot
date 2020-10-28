package org.springframework.boot.buildpack.platform.docker.configuration;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Docker registry authentication configuration using a token.
 *

 */
class DockerRegistryTokenAuthentication extends JsonEncodedDockerRegistryAuthentication {

	@JsonProperty("identitytoken")
	private final String token;

	DockerRegistryTokenAuthentication(String token) {
		this.token = token;
		createAuthHeader();
	}

	String getToken() {
		return this.token;
	}

}
