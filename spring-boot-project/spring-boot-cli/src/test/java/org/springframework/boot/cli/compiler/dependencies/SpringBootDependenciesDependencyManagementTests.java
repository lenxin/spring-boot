package org.springframework.boot.cli.compiler.dependencies;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Tests for {@link SpringBootDependenciesDependencyManagement}
 *

 */
class SpringBootDependenciesDependencyManagementTests {

	private final DependencyManagement dependencyManagement = new SpringBootDependenciesDependencyManagement();

	@Test
	void springBootVersion() {
		assertThat(this.dependencyManagement.getSpringBootVersion()).isNotNull();
	}

	@Test
	void find() {
		Dependency dependency = this.dependencyManagement.find("spring-boot");
		assertThat(dependency).isNotNull();
		assertThat(dependency.getGroupId()).isEqualTo("org.springframework.boot");
		assertThat(dependency.getArtifactId()).isEqualTo("spring-boot");
	}

	@Test
	void getDependencies() {
		assertThat(this.dependencyManagement.getDependencies()).isNotEmpty();
	}

}
