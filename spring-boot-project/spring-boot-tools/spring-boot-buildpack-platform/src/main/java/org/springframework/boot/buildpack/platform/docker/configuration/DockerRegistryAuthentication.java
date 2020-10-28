package org.springframework.boot.buildpack.platform.docker.configuration;

/**
 * Docker registry authentication configuration.
 *

 * @since 2.4.0
 */
public interface DockerRegistryAuthentication {

	/**
	 * Returns the auth header that should be used for docker authentication.
	 * @return the auth header
	 */
	String getAuthHeader();

}
