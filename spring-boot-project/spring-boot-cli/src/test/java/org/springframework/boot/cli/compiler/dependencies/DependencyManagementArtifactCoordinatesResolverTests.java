package org.springframework.boot.cli.compiler.dependencies;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

/**
 * Tests for {@link DependencyManagementArtifactCoordinatesResolver}.
 *


 */
class DependencyManagementArtifactCoordinatesResolverTests {

	private DependencyManagement dependencyManagement;

	private DependencyManagementArtifactCoordinatesResolver resolver;

	@BeforeEach
	void setup() {
		this.dependencyManagement = mock(DependencyManagement.class);
		given(this.dependencyManagement.find("a1")).willReturn(new Dependency("g1", "a1", "0"));
		given(this.dependencyManagement.getSpringBootVersion()).willReturn("1");
		this.resolver = new DependencyManagementArtifactCoordinatesResolver(this.dependencyManagement);
	}

	@Test
	void getGroupIdForBootArtifact() {
		assertThat(this.resolver.getGroupId("spring-boot-something")).isEqualTo("org.springframework.boot");
		verify(this.dependencyManagement, never()).find(anyString());
	}

	@Test
	void getGroupIdFound() {
		assertThat(this.resolver.getGroupId("a1")).isEqualTo("g1");
	}

	@Test
	void getGroupIdNotFound() {
		assertThat(this.resolver.getGroupId("a2")).isNull();
	}

}
