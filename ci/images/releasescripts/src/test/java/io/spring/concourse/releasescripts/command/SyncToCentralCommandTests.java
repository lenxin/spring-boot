package io.spring.concourse.releasescripts.command;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.spring.concourse.releasescripts.ReleaseInfo;
import io.spring.concourse.releasescripts.artifactory.ArtifactoryService;
import io.spring.concourse.releasescripts.bintray.BintrayService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import org.springframework.boot.DefaultApplicationArguments;
import org.springframework.core.io.ClassPathResource;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;

/**
 * Tests for {@link SyncToCentralCommand}.
 *

 */
class SyncToCentralCommandTests {

	@Mock
	private BintrayService service;

	private SyncToCentralCommand command;

	private ObjectMapper objectMapper;

	@BeforeEach
	void setup() {
		MockitoAnnotations.initMocks(this);
		this.objectMapper = new ObjectMapper().disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
		this.command = new SyncToCentralCommand(this.service, objectMapper);
	}

	@Test
	void runWhenReleaseTypeNotSpecifiedShouldThrowException() throws Exception {
		Assertions.assertThatIllegalStateException()
				.isThrownBy(() -> this.command.run(new DefaultApplicationArguments("syncToCentral")));
	}

	@Test
	void runWhenReleaseTypeMilestoneShouldDoNothing() throws Exception {
		this.command.run(new DefaultApplicationArguments("syncToCentral", "M", getBuildInfoLocation()));
		verifyNoInteractions(this.service);
	}

	@Test
	void runWhenReleaseTypeRCShouldDoNothing() throws Exception {
		this.command.run(new DefaultApplicationArguments("syncToCentral", "RC", getBuildInfoLocation()));
		verifyNoInteractions(this.service);
	}

	@Test
	void runWhenReleaseTypeReleaseShouldCallService() throws Exception {
		ArgumentCaptor<ReleaseInfo> captor = ArgumentCaptor.forClass(ReleaseInfo.class);
		this.command.run(new DefaultApplicationArguments("syncToCentral", "RELEASE", getBuildInfoLocation()));
		verify(this.service).syncToMavenCentral(captor.capture());
		ReleaseInfo releaseInfo = captor.getValue();
		assertThat(releaseInfo.getBuildName()).isEqualTo("example");
		assertThat(releaseInfo.getBuildNumber()).isEqualTo("example-build-1");
		assertThat(releaseInfo.getGroupId()).isEqualTo("org.example.demo");
		assertThat(releaseInfo.getVersion()).isEqualTo("2.2.0");
	}

	private String getBuildInfoLocation() throws Exception {
		return new ClassPathResource("build-info-response.json", ArtifactoryService.class).getFile().getAbsolutePath();
	}

}
