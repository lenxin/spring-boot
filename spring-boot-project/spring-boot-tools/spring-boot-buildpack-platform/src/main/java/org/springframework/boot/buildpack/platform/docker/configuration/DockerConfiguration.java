package org.springframework.boot.buildpack.platform.docker.configuration;

import org.springframework.util.Assert;

/**
 * Docker configuration options.
 *


 * @since 2.4.0
 */
public final class DockerConfiguration {

	private final DockerHost host;

	private final DockerRegistryAuthentication builderAuthentication;

	private final DockerRegistryAuthentication publishAuthentication;

	public DockerConfiguration() {
		this(null, null, null);
	}

	private DockerConfiguration(DockerHost host, DockerRegistryAuthentication builderAuthentication,
			DockerRegistryAuthentication publishAuthentication) {
		this.host = host;
		this.builderAuthentication = builderAuthentication;
		this.publishAuthentication = publishAuthentication;
	}

	public DockerHost getHost() {
		return this.host;
	}

	public DockerRegistryAuthentication getBuilderRegistryAuthentication() {
		return this.builderAuthentication;
	}

	public DockerRegistryAuthentication getPublishRegistryAuthentication() {
		return this.publishAuthentication;
	}

	public DockerConfiguration withHost(String address, boolean secure, String certificatePath) {
		Assert.notNull(address, "Address must not be null");
		return new DockerConfiguration(new DockerHost(address, secure, certificatePath), this.builderAuthentication,
				this.publishAuthentication);
	}

	public DockerConfiguration withBuilderRegistryTokenAuthentication(String token) {
		Assert.notNull(token, "Token must not be null");
		return new DockerConfiguration(this.host, new DockerRegistryTokenAuthentication(token),
				this.publishAuthentication);
	}

	public DockerConfiguration withBuilderRegistryUserAuthentication(String username, String password, String url,
			String email) {
		Assert.notNull(username, "Username must not be null");
		Assert.notNull(password, "Password must not be null");
		return new DockerConfiguration(this.host, new DockerRegistryUserAuthentication(username, password, url, email),
				this.publishAuthentication);
	}

	public DockerConfiguration withPublishRegistryTokenAuthentication(String token) {
		Assert.notNull(token, "Token must not be null");
		return new DockerConfiguration(this.host, this.builderAuthentication,
				new DockerRegistryTokenAuthentication(token));
	}

	public DockerConfiguration withPublishRegistryUserAuthentication(String username, String password, String url,
			String email) {
		Assert.notNull(username, "Username must not be null");
		Assert.notNull(password, "Password must not be null");
		return new DockerConfiguration(this.host, this.builderAuthentication,
				new DockerRegistryUserAuthentication(username, password, url, email));
	}

}
