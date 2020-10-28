package org.springframework.boot.cli.compiler.dependencies;

import java.util.Arrays;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;

/**
 * Tests for {@link CompositeDependencyManagement}
 *

 */
@ExtendWith(MockitoExtension.class)
class CompositeDependencyManagementTests {

	@Mock
	private DependencyManagement dependencyManagement1;

	@Mock
	private DependencyManagement dependencyManagement2;

	@Test
	void unknownSpringBootVersion() {
		given(this.dependencyManagement1.getSpringBootVersion()).willReturn(null);
		given(this.dependencyManagement2.getSpringBootVersion()).willReturn(null);
		assertThat(new CompositeDependencyManagement(this.dependencyManagement1, this.dependencyManagement2)
				.getSpringBootVersion()).isNull();
	}

	@Test
	void knownSpringBootVersion() {
		given(this.dependencyManagement1.getSpringBootVersion()).willReturn("1.2.3");
		assertThat(new CompositeDependencyManagement(this.dependencyManagement1, this.dependencyManagement2)
				.getSpringBootVersion()).isEqualTo("1.2.3");
	}

	@Test
	void unknownDependency() {
		given(this.dependencyManagement1.find("artifact")).willReturn(null);
		given(this.dependencyManagement2.find("artifact")).willReturn(null);
		assertThat(new CompositeDependencyManagement(this.dependencyManagement1, this.dependencyManagement2)
				.find("artifact")).isNull();
	}

	@Test
	void knownDependency() {
		given(this.dependencyManagement1.find("artifact")).willReturn(new Dependency("test", "artifact", "1.2.3"));
		assertThat(new CompositeDependencyManagement(this.dependencyManagement1, this.dependencyManagement2)
				.find("artifact")).isEqualTo(new Dependency("test", "artifact", "1.2.3"));
	}

	@Test
	void getDependencies() {
		given(this.dependencyManagement1.getDependencies())
				.willReturn(Arrays.asList(new Dependency("test", "artifact", "1.2.3")));
		given(this.dependencyManagement2.getDependencies())
				.willReturn(Arrays.asList(new Dependency("test", "artifact", "1.2.4")));
		assertThat(new CompositeDependencyManagement(this.dependencyManagement1, this.dependencyManagement2)
				.getDependencies()).containsOnly(new Dependency("test", "artifact", "1.2.3"),
						new Dependency("test", "artifact", "1.2.4"));
	}

}
