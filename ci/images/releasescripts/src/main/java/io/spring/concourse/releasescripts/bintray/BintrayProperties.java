package io.spring.concourse.releasescripts.bintray;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * {@link ConfigurationProperties @ConfigurationProperties} for the Bintray API.
 *

 */
@ConfigurationProperties(prefix = "bintray")
public class BintrayProperties {

	private String username;

	private String apiKey;

	private String repo;

	private String subject;

	public String getUsername() {
		return this.username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getApiKey() {
		return this.apiKey;
	}

	public void setApiKey(String apiKey) {
		this.apiKey = apiKey;
	}

	public String getRepo() {
		return this.repo;
	}

	public void setRepo(String repo) {
		this.repo = repo;
	}

	public String getSubject() {
		return this.subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

}
