package org.springframework.boot.maven;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import org.apache.maven.artifact.Artifact;
import org.apache.maven.shared.artifact.filter.collection.ArtifactFilterException;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

/**
 * Tests for {@link org.springframework.boot.maven.IncludeFilter}.
 *

 */
@SuppressWarnings({ "rawtypes", "unchecked" })
class IncludeFilterTests {

	@Test
	void includeSimple() throws ArtifactFilterException {
		IncludeFilter filter = new IncludeFilter(Arrays.asList(createInclude("com.foo", "bar")));
		Artifact artifact = createArtifact("com.foo", "bar");
		Set result = filter.filter(Collections.singleton(artifact));
		assertThat(result).hasSize(1);
		assertThat(result.iterator().next()).isSameAs(artifact);
	}

	@Test
	void includeGroupIdNoMatch() throws ArtifactFilterException {
		IncludeFilter filter = new IncludeFilter(Arrays.asList(createInclude("com.foo", "bar")));
		Artifact artifact = createArtifact("com.baz", "bar");
		Set result = filter.filter(Collections.singleton(artifact));
		assertThat(result).isEmpty();
	}

	@Test
	void includeArtifactIdNoMatch() throws ArtifactFilterException {
		IncludeFilter filter = new IncludeFilter(Arrays.asList(createInclude("com.foo", "bar")));
		Artifact artifact = createArtifact("com.foo", "biz");
		Set result = filter.filter(Collections.singleton(artifact));
		assertThat(result).isEmpty();
	}

	@Test
	void includeClassifier() throws ArtifactFilterException {
		IncludeFilter filter = new IncludeFilter(Arrays.asList(createInclude("com.foo", "bar", "jdk5")));
		Artifact artifact = createArtifact("com.foo", "bar", "jdk5");
		Set result = filter.filter(Collections.singleton(artifact));
		assertThat(result).hasSize(1);
		assertThat(result.iterator().next()).isSameAs(artifact);
	}

	@Test
	void includeClassifierNoTargetClassifier() throws ArtifactFilterException {
		IncludeFilter filter = new IncludeFilter(Arrays.asList(createInclude("com.foo", "bar", "jdk5")));
		Artifact artifact = createArtifact("com.foo", "bar");
		Set result = filter.filter(Collections.singleton(artifact));
		assertThat(result).isEmpty();
	}

	@Test
	void includeClassifierNoMatch() throws ArtifactFilterException {
		IncludeFilter filter = new IncludeFilter(Arrays.asList(createInclude("com.foo", "bar", "jdk5")));
		Artifact artifact = createArtifact("com.foo", "bar", "jdk6");
		Set result = filter.filter(Collections.singleton(artifact));
		assertThat(result).isEmpty();
	}

	@Test
	void includeMulti() throws ArtifactFilterException {
		IncludeFilter filter = new IncludeFilter(Arrays.asList(createInclude("com.foo", "bar"),
				createInclude("com.foo", "bar2"), createInclude("org.acme", "app")));
		Set<Artifact> artifacts = new HashSet<>();
		artifacts.add(createArtifact("com.foo", "bar"));
		artifacts.add(createArtifact("com.foo", "bar"));
		Artifact anotherAcme = createArtifact("org.acme", "another-app");
		artifacts.add(anotherAcme);
		Set result = filter.filter(artifacts);
		assertThat(result).hasSize(2);
	}

	private Include createInclude(String groupId, String artifactId) {
		return createInclude(groupId, artifactId, null);
	}

	private Include createInclude(String groupId, String artifactId, String classifier) {
		Include include = new Include();
		include.setGroupId(groupId);
		include.setArtifactId(artifactId);
		if (classifier != null) {
			include.setClassifier(classifier);
		}
		return include;
	}

	private Artifact createArtifact(String groupId, String artifactId, String classifier) {
		Artifact a = mock(Artifact.class);
		given(a.getGroupId()).willReturn(groupId);
		given(a.getArtifactId()).willReturn(artifactId);
		given(a.getClassifier()).willReturn(classifier);
		return a;
	}

	private Artifact createArtifact(String groupId, String artifactId) {
		return createArtifact(groupId, artifactId, null);
	}

}
