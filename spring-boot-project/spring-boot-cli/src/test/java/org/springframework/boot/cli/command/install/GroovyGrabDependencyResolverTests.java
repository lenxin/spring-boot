package org.springframework.boot.cli.command.install;

import java.io.File;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.springframework.boot.cli.compiler.GroovyCompilerConfiguration;
import org.springframework.boot.cli.compiler.GroovyCompilerScope;
import org.springframework.boot.cli.compiler.RepositoryConfigurationFactory;
import org.springframework.boot.cli.compiler.grape.RepositoryConfiguration;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Tests for {@link GroovyGrabDependencyResolver}.
 *

 */
class GroovyGrabDependencyResolverTests {

	private DependencyResolver resolver;

	@BeforeEach
	void setupResolver() {
		GroovyCompilerConfiguration configuration = new GroovyCompilerConfiguration() {

			@Override
			public boolean isGuessImports() {
				return true;
			}

			@Override
			public boolean isGuessDependencies() {
				return true;
			}

			@Override
			public boolean isAutoconfigure() {
				return false;
			}

			@Override
			public GroovyCompilerScope getScope() {
				return GroovyCompilerScope.DEFAULT;
			}

			@Override
			public List<RepositoryConfiguration> getRepositoryConfiguration() {
				return RepositoryConfigurationFactory.createDefaultRepositoryConfiguration();
			}

			@Override
			public String[] getClasspath() {
				return new String[] { "." };
			}

			@Override
			public boolean isQuiet() {
				return false;
			}

		};
		this.resolver = new GroovyGrabDependencyResolver(configuration);
	}

	@Test
	void resolveArtifactWithNoDependencies() throws Exception {
		List<File> resolved = this.resolver.resolve(Arrays.asList("commons-logging:commons-logging:1.1.3"));
		assertThat(resolved).hasSize(1);
		assertThat(getNames(resolved)).containsOnly("commons-logging-1.1.3.jar");
	}

	@Test
	void resolveArtifactWithDependencies() throws Exception {
		List<File> resolved = this.resolver.resolve(Arrays.asList("org.springframework:spring-core:4.1.1.RELEASE"));
		assertThat(resolved).hasSize(2);
		assertThat(getNames(resolved)).containsOnly("commons-logging-1.1.3.jar", "spring-core-4.1.1.RELEASE.jar");
	}

	@Test
	void resolveShorthandArtifactWithDependencies() throws Exception {
		List<File> resolved = this.resolver.resolve(Arrays.asList("spring-beans"));
		assertThat(resolved).hasSize(3);
		Set<String> names = getNames(resolved);
		assertThat(names).anyMatch((name) -> name.startsWith("spring-core-"));
		assertThat(names).anyMatch((name) -> name.startsWith("spring-beans-"));
		assertThat(names).anyMatch((name) -> name.startsWith("spring-jcl-"));
	}

	@Test
	void resolveMultipleArtifacts() throws Exception {
		List<File> resolved = this.resolver
				.resolve(Arrays.asList("junit:junit:4.11", "commons-logging:commons-logging:1.1.3"));
		assertThat(resolved).hasSize(4);
		assertThat(getNames(resolved)).containsOnly("junit-4.11.jar", "commons-logging-1.1.3.jar",
				"hamcrest-core-2.2.jar", "hamcrest-2.2.jar");
	}

	Set<String> getNames(Collection<File> files) {
		Set<String> names = new HashSet<>(files.size());
		for (File file : files) {
			names.add(file.getName());
		}
		return names;
	}

}
