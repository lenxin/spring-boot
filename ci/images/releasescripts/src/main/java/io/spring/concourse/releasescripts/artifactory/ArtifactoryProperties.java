package io.spring.concourse.releasescripts.artifactory;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * {@link ConfigurationProperties @ConfigurationProperties} for an Artifactory server.
 *

 */
@ConfigurationProperties(prefix = "artifactory")
public class ArtifactoryProperties {

	private String username;

	private String password;

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

}
