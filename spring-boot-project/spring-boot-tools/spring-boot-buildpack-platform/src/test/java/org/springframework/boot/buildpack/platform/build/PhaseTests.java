package org.springframework.boot.buildpack.platform.build;

import org.junit.jupiter.api.Test;

import org.springframework.boot.buildpack.platform.docker.type.ContainerConfig.Update;
import org.springframework.boot.buildpack.platform.docker.type.VolumeName;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;

/**
 * Tests for {@link Phase}.
 *


 */
class PhaseTests {

	private static final String[] NO_ARGS = {};

	@Test
	void getNameReturnsName() {
		Phase phase = new Phase("test", false);
		assertThat(phase.getName()).isEqualTo("test");
	}

	@Test
	void toStringReturnsName() {
		Phase phase = new Phase("test", false);
		assertThat(phase).hasToString("test");
	}

	@Test
	void applyUpdatesConfiguration() {
		Phase phase = new Phase("test", false);
		Update update = mock(Update.class);
		phase.apply(update);
		verify(update).withCommand("/cnb/lifecycle/test", NO_ARGS);
		verify(update).withLabel("author", "spring-boot");
		verifyNoMoreInteractions(update);
	}

	@Test
	void applyWhenWithDaemonAccessUpdatesConfigurationWithRootUserAndDomainSocketBinding() {
		Phase phase = new Phase("test", false);
		phase.withDaemonAccess();
		Update update = mock(Update.class);
		phase.apply(update);
		verify(update).withUser("root");
		verify(update).withBind("/var/run/docker.sock", "/var/run/docker.sock");
		verify(update).withCommand("/cnb/lifecycle/test", NO_ARGS);
		verify(update).withLabel("author", "spring-boot");
		verifyNoMoreInteractions(update);
	}

	@Test
	void applyWhenWithLogLevelArgAndVerboseLoggingUpdatesConfigurationWithLogLevel() {
		Phase phase = new Phase("test", true);
		phase.withLogLevelArg();
		Update update = mock(Update.class);
		phase.apply(update);
		verify(update).withCommand("/cnb/lifecycle/test", "-log-level", "debug");
		verify(update).withLabel("author", "spring-boot");
		verifyNoMoreInteractions(update);
	}

	@Test
	void applyWhenWithLogLevelArgAndNonVerboseLoggingDoesNotUpdateLogLevel() {
		Phase phase = new Phase("test", false);
		phase.withLogLevelArg();
		Update update = mock(Update.class);
		phase.apply(update);
		verify(update).withCommand("/cnb/lifecycle/test");
		verify(update).withLabel("author", "spring-boot");
		verifyNoMoreInteractions(update);
	}

	@Test
	void applyWhenWithArgsUpdatesConfigurationWithArguments() {
		Phase phase = new Phase("test", false);
		phase.withArgs("a", "b", "c");
		Update update = mock(Update.class);
		phase.apply(update);
		verify(update).withCommand("/cnb/lifecycle/test", "a", "b", "c");
		verify(update).withLabel("author", "spring-boot");
		verifyNoMoreInteractions(update);
	}

	@Test
	void applyWhenWithBindsUpdatesConfigurationWithBinds() {
		Phase phase = new Phase("test", false);
		VolumeName volumeName = VolumeName.of("test");
		phase.withBinds(volumeName, "/test");
		Update update = mock(Update.class);
		phase.apply(update);
		verify(update).withCommand("/cnb/lifecycle/test");
		verify(update).withLabel("author", "spring-boot");
		verify(update).withBind(volumeName, "/test");
		verifyNoMoreInteractions(update);
	}

	@Test
	void applyWhenWithEnvUpdatesConfigurationWithEnv() {
		Phase phase = new Phase("test", false);
		phase.withEnv("name1", "value1");
		phase.withEnv("name2", "value2");
		Update update = mock(Update.class);
		phase.apply(update);
		verify(update).withCommand("/cnb/lifecycle/test");
		verify(update).withLabel("author", "spring-boot");
		verify(update).withEnv("name1", "value1");
		verify(update).withEnv("name2", "value2");
		verifyNoMoreInteractions(update);
	}

}
