package org.springframework.boot.buildpack.platform.docker.configuration;

import com.fasterxml.jackson.core.JsonProcessingException;

import org.springframework.boot.buildpack.platform.json.SharedObjectMapper;
import org.springframework.util.Base64Utils;

/**
 * {@link DockerRegistryAuthentication} that uses a Base64 encoded auth header value based
 * on the JSON created from the instance.
 *

 */
class JsonEncodedDockerRegistryAuthentication implements DockerRegistryAuthentication {

	private String authHeader;

	@Override
	public String getAuthHeader() {
		return this.authHeader;
	}

	protected void createAuthHeader() {
		try {
			this.authHeader = Base64Utils.encodeToUrlSafeString(SharedObjectMapper.get().writeValueAsBytes(this));
		}
		catch (JsonProcessingException ex) {
			throw new IllegalStateException("Error creating Docker registry authentication header", ex);
		}
	}

}
