package io.spring.concourse.releasescripts;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * {@link ConfigurationProperties @ConfigurationProperties} corresponding to the release.
 *

 */
@ConfigurationProperties(prefix = "release")
public class ReleaseProperties {

	private String buildName;

	private String buildNumber;

	private String groupId;

	private String version;

	public String getBuildName() {
		return this.buildName;
	}

	public void setBuildName(String buildName) {
		this.buildName = buildName;
	}

	public String getBuildNumber() {
		return this.buildNumber;
	}

	public void setBuildNumber(String buildNumber) {
		this.buildNumber = buildNumber;
	}

	public String getGroupId() {
		return this.groupId;
	}

	public void setGroupId(String groupId) {
		this.groupId = groupId;
	}

	public String getVersion() {
		return this.version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

}
