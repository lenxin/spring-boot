package io.spring.concourse.releasescripts.command;

import java.io.File;
import java.nio.file.Files;
import java.util.List;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.spring.concourse.releasescripts.ReleaseInfo;
import io.spring.concourse.releasescripts.ReleaseType;
import io.spring.concourse.releasescripts.artifactory.payload.BuildInfoResponse;
import io.spring.concourse.releasescripts.bintray.BintrayService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.boot.ApplicationArguments;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

/**
 * Command used to sync artifacts to Maven Central.
 *

 */
@Component
public class SyncToCentralCommand implements Command {

	private static final Logger logger = LoggerFactory.getLogger(SyncToCentralCommand.class);

	private static final String SYNC_TO_CENTRAL_COMMAND = "syncToCentral";

	private final BintrayService service;

	private final ObjectMapper objectMapper;

	public SyncToCentralCommand(BintrayService service, ObjectMapper objectMapper) {
		this.service = service;
		this.objectMapper = objectMapper;
	}

	@Override
	public String getName() {
		return SYNC_TO_CENTRAL_COMMAND;
	}

	@Override
	public void run(ApplicationArguments args) throws Exception {
		logger.debug("Running 'sync to central' command");
		List<String> nonOptionArgs = args.getNonOptionArgs();
		Assert.state(!nonOptionArgs.isEmpty(), "No command argument specified");
		Assert.state(nonOptionArgs.size() == 3, "Release type or build info not specified");
		String releaseType = nonOptionArgs.get(1);
		ReleaseType type = ReleaseType.from(releaseType);
		if (!ReleaseType.RELEASE.equals(type)) {
			return;
		}
		String buildInfoLocation = nonOptionArgs.get(2);
		byte[] content = Files.readAllBytes(new File(buildInfoLocation).toPath());
		BuildInfoResponse buildInfoResponse = this.objectMapper.readValue(content, BuildInfoResponse.class);
		ReleaseInfo releaseInfo = ReleaseInfo.from(buildInfoResponse.getBuildInfo());
		this.service.syncToMavenCentral(releaseInfo);
	}

}
