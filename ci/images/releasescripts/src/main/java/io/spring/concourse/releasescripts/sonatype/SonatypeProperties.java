package io.spring.concourse.releasescripts.sonatype;

import com.fasterxml.jackson.annotation.JsonProperty;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * {@link ConfigurationProperties @ConfigurationProperties} for Sonatype.
 *

 */
@ConfigurationProperties(prefix = "sonatype")
public class SonatypeProperties {

	@JsonProperty("username")
	private String userToken;

	@JsonProperty("password")
	private String passwordToken;

	public String getUserToken() {
		return this.userToken;
	}

	public void setUserToken(String userToken) {
		this.userToken = userToken;
	}

	public String getPasswordToken() {
		return this.passwordToken;
	}

	public void setPasswordToken(String passwordToken) {
		this.passwordToken = passwordToken;
	}

}
