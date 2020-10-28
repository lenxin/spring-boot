package org.springframework.boot.buildpack.platform.docker.configuration;

/**
 * Docker host connection options.
 *

 * @since 2.4.0
 */
public class DockerHost {

	private final String address;

	private final boolean secure;

	private final String certificatePath;

	public DockerHost(String address, boolean secure, String certificatePath) {
		this.address = address;
		this.secure = secure;
		this.certificatePath = certificatePath;
	}

	public String getAddress() {
		return this.address;
	}

	public boolean isSecure() {
		return this.secure;
	}

	public String getCertificatePath() {
		return this.certificatePath;
	}

}
