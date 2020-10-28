package org.springframework.boot.cli.compiler.dependencies;

import org.springframework.util.StringUtils;

/**
 * {@link ArtifactCoordinatesResolver} backed by
 * {@link SpringBootDependenciesDependencyManagement}.
 *


 * @since 1.0.0
 */
public class DependencyManagementArtifactCoordinatesResolver implements ArtifactCoordinatesResolver {

	private final DependencyManagement dependencyManagement;

	public DependencyManagementArtifactCoordinatesResolver() {
		this(new SpringBootDependenciesDependencyManagement());
	}

	public DependencyManagementArtifactCoordinatesResolver(DependencyManagement dependencyManagement) {
		this.dependencyManagement = dependencyManagement;
	}

	@Override
	public String getGroupId(String artifactId) {
		Dependency dependency = find(artifactId);
		return (dependency != null) ? dependency.getGroupId() : null;
	}

	@Override
	public String getArtifactId(String id) {
		Dependency dependency = find(id);
		return (dependency != null) ? dependency.getArtifactId() : null;
	}

	private Dependency find(String id) {
		if (StringUtils.countOccurrencesOf(id, ":") == 2) {
			String[] tokens = id.split(":");
			return new Dependency(tokens[0], tokens[1], tokens[2]);
		}
		if (id != null) {
			if (id.startsWith("spring-boot")) {
				return new Dependency("org.springframework.boot", id, this.dependencyManagement.getSpringBootVersion());
			}
			return this.dependencyManagement.find(id);
		}
		return null;
	}

	@Override
	public String getVersion(String module) {
		Dependency dependency = find(module);
		return (dependency != null) ? dependency.getVersion() : null;
	}

}
