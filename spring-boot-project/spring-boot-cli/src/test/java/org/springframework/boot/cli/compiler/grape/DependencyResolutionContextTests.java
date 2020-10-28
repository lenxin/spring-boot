package org.springframework.boot.cli.compiler.grape;

import org.junit.jupiter.api.Test;

import org.springframework.boot.cli.compiler.dependencies.SpringBootDependenciesDependencyManagement;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Tests for {@link DependencyResolutionContext}.
 *

 */
class DependencyResolutionContextTests {

	@Test
	void defaultDependenciesEmpty() {
		assertThat(new DependencyResolutionContext().getManagedDependencies()).isEmpty();
	}

	@Test
	void canAddSpringBootDependencies() {
		DependencyResolutionContext dependencyResolutionContext = new DependencyResolutionContext();
		dependencyResolutionContext.addDependencyManagement(new SpringBootDependenciesDependencyManagement());
		assertThat(dependencyResolutionContext.getManagedDependencies()).isNotEmpty();
	}

}
