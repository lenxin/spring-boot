package org.springframework.boot.maven;

import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Path;
import java.util.jar.JarOutputStream;
import java.util.jar.Manifest;

import org.apache.maven.artifact.Artifact;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

/**
 * Tests for {@link JarTypeFilter}.
 *

 */
class JarTypeFilterTests {

	@TempDir
	Path temp;

	@Test
	void whenArtifactHasNoJarTypeThenItIsIncluded() {
		assertThat(new JarTypeFilter().filter(createArtifact(null))).isFalse();
	}

	@Test
	void whenArtifactHasJarTypeThatIsNotExcludedThenItIsIncluded() {
		assertThat(new JarTypeFilter().filter(createArtifact("something-included"))).isFalse();
	}

	@Test
	void whenArtifactHasDependenciesStarterJarTypeThenItIsExcluded() {
		assertThat(new JarTypeFilter().filter(createArtifact("dependencies-starter"))).isTrue();
	}

	@Test
	void whenArtifactHasAnnotationProcessorJarTypeThenItIsExcluded() {
		assertThat(new JarTypeFilter().filter(createArtifact("annotation-processor"))).isTrue();
	}

	private Artifact createArtifact(String jarType) {
		Path jarPath = this.temp.resolve("test.jar");
		Manifest manifest = new Manifest();
		manifest.getMainAttributes().putValue("Manifest-Version", "1.0");
		if (jarType != null) {
			manifest.getMainAttributes().putValue("Spring-Boot-Jar-Type", jarType);
		}
		try {
			new JarOutputStream(new FileOutputStream(jarPath.toFile()), manifest).close();
		}
		catch (IOException ex) {
			throw new RuntimeException(ex);
		}
		Artifact artifact = mock(Artifact.class);
		given(artifact.getFile()).willReturn(jarPath.toFile());
		return artifact;
	}

}
