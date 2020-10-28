package io.spring.concourse.releasescripts.command;

import java.io.File;
import java.nio.file.Files;
import java.util.List;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.spring.concourse.releasescripts.ReleaseInfo;
import io.spring.concourse.releasescripts.ReleaseType;
import io.spring.concourse.releasescripts.artifactory.ArtifactoryService;
import io.spring.concourse.releasescripts.artifactory.payload.BuildInfoResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.boot.ApplicationArguments;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

/**
 * Command used to move the build artifacts to a target repository in Artifactory.
 *

 */
@Component
public class PromoteCommand implements Command {

	private static final Logger logger = LoggerFactory.getLogger(PromoteCommand.class);

	private final ArtifactoryService service;

	private final ObjectMapper objectMapper;

	public PromoteCommand(ArtifactoryService service, ObjectMapper objectMapper) {
		this.service = service;
		this.objectMapper = objectMapper;
	}

	@Override
	public void run(ApplicationArguments args) throws Exception {
		logger.debug("Running 'promote' command");
		List<String> nonOptionArgs = args.getNonOptionArgs();
		Assert.state(!nonOptionArgs.isEmpty(), "No command argument specified");
		Assert.state(nonOptionArgs.size() == 3, "Release type or build info location not specified");
		String releaseType = nonOptionArgs.get(1);
		ReleaseType type = ReleaseType.from(releaseType);
		String buildInfoLocation = nonOptionArgs.get(2);
		byte[] content = Files.readAllBytes(new File(buildInfoLocation).toPath());
		BuildInfoResponse buildInfoResponse = this.objectMapper.readValue(new String(content), BuildInfoResponse.class);
		ReleaseInfo releaseInfo = ReleaseInfo.from(buildInfoResponse.getBuildInfo());
		this.service.promote(type.getRepo(), releaseInfo);
	}

}
