package org.springframework.boot.build.artifactory;

import org.gradle.api.Project;
import org.gradle.testfixtures.ProjectBuilder;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Tests for {@link ArtifactoryRepository}.
 *

 */
public class ArtifactoryRepositoryTests {

	@Test
	void whenProjectVersionIsMilestoneThenRepositoryIsMilestone() {
		Project project = ProjectBuilder.builder().build();
		project.setVersion("1.2.3-M1");
		assertThat(ArtifactoryRepository.forProject(project).getName()).isEqualTo("milestone");
	}

	@Test
	void whenProjectVersionIsReleaseCandidateThenRepositoryIsMilestone() {
		Project project = ProjectBuilder.builder().build();
		project.setVersion("1.2.3-RC1");
		assertThat(ArtifactoryRepository.forProject(project).getName()).isEqualTo("milestone");
	}

	@Test
	void whenProjectVersionIsReleaseThenRepositoryIsRelease() {
		Project project = ProjectBuilder.builder().build();
		project.setVersion("1.2.3");
		assertThat(ArtifactoryRepository.forProject(project).getName()).isEqualTo("release");
	}

	@Test
	void whenProjectVersionIsSnapshotThenRepositoryIsSnapshot() {
		Project project = ProjectBuilder.builder().build();
		project.setVersion("1.2.3-SNAPSHOT");
		assertThat(ArtifactoryRepository.forProject(project).getName()).isEqualTo("snapshot");
	}

}
