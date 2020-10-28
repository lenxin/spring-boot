package io.spring.concourse.releasescripts;

import io.spring.concourse.releasescripts.artifactory.payload.BuildInfoResponse;

import org.springframework.boot.context.properties.PropertyMapper;
import org.springframework.util.StringUtils;

/**
 * Properties corresponding to the release.
 *

 */
public class ReleaseInfo {

	private String buildName;

	private String buildNumber;

	private String groupId;

	private String version;

	public static ReleaseInfo from(BuildInfoResponse.BuildInfo buildInfo) {
		ReleaseInfo info = new ReleaseInfo();
		PropertyMapper propertyMapper = PropertyMapper.get();
		propertyMapper.from(buildInfo.getName()).to(info::setBuildName);
		propertyMapper.from(buildInfo.getNumber()).to(info::setBuildNumber);
		String[] moduleInfo = StringUtils.delimitedListToStringArray(buildInfo.getModules()[0].getId(), ":");
		propertyMapper.from(moduleInfo[0]).to(info::setGroupId);
		propertyMapper.from(moduleInfo[2]).to(info::setVersion);
		return info;
	}

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
