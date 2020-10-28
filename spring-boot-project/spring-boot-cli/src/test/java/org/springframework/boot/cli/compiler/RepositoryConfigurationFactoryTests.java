package org.springframework.boot.cli.compiler;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.jupiter.api.Test;

import org.springframework.boot.cli.compiler.grape.RepositoryConfiguration;
import org.springframework.boot.test.util.TestPropertyValues;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Tests for {@link RepositoryConfigurationFactory}
 *

 */
class RepositoryConfigurationFactoryTests {

	@Test
	void defaultRepositories() {
		TestPropertyValues.of("user.home:src/test/resources/maven-settings/basic").applyToSystemProperties(() -> {
			List<RepositoryConfiguration> repositoryConfiguration = RepositoryConfigurationFactory
					.createDefaultRepositoryConfiguration();
			assertRepositoryConfiguration(repositoryConfiguration, "central", "local", "spring-snapshot",
					"spring-milestone");
			return null;
		});
	}

	@Test
	void snapshotRepositoriesDisabled() {
		TestPropertyValues.of("user.home:src/test/resources/maven-settings/basic", "disableSpringSnapshotRepos:true")
				.applyToSystemProperties(() -> {
					List<RepositoryConfiguration> repositoryConfiguration = RepositoryConfigurationFactory
							.createDefaultRepositoryConfiguration();
					assertRepositoryConfiguration(repositoryConfiguration, "central", "local");
					return null;
				});
	}

	@Test
	void activeByDefaultProfileRepositories() {
		TestPropertyValues.of("user.home:src/test/resources/maven-settings/active-profile-repositories")
				.applyToSystemProperties(() -> {
					List<RepositoryConfiguration> repositoryConfiguration = RepositoryConfigurationFactory
							.createDefaultRepositoryConfiguration();
					assertRepositoryConfiguration(repositoryConfiguration, "central", "local", "spring-snapshot",
							"spring-milestone", "active-by-default");
					return null;
				});
	}

	@Test
	void activeByPropertyProfileRepositories() {
		TestPropertyValues.of("user.home:src/test/resources/maven-settings/active-profile-repositories", "foo:bar")
				.applyToSystemProperties(() -> {
					List<RepositoryConfiguration> repositoryConfiguration = RepositoryConfigurationFactory
							.createDefaultRepositoryConfiguration();
					assertRepositoryConfiguration(repositoryConfiguration, "central", "local", "spring-snapshot",
							"spring-milestone", "active-by-property");
					return null;
				});
	}

	@Test
	void interpolationProfileRepositories() {
		TestPropertyValues
				.of("user.home:src/test/resources/maven-settings/active-profile-repositories", "interpolate:true")
				.applyToSystemProperties(() -> {
					List<RepositoryConfiguration> repositoryConfiguration = RepositoryConfigurationFactory
							.createDefaultRepositoryConfiguration();
					assertRepositoryConfiguration(repositoryConfiguration, "central", "local", "spring-snapshot",
							"spring-milestone", "interpolate-releases", "interpolate-snapshots");
					return null;
				});
	}

	private void assertRepositoryConfiguration(List<RepositoryConfiguration> configurations, String... expectedNames) {
		assertThat(configurations).hasSize(expectedNames.length);
		Set<String> actualNames = new HashSet<>();
		for (RepositoryConfiguration configuration : configurations) {
			actualNames.add(configuration.getName());
		}
		assertThat(actualNames).containsOnly(expectedNames);
	}

}
