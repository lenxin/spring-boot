package org.springframework.boot.maven;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.jar.JarFile;

import org.apache.maven.artifact.Artifact;

/**
 * A {@link DependencyFilter} that filters dependencies based on the jar type declared in
 * their manifest.
 *

 */
class JarTypeFilter extends DependencyFilter {

	private static final Set<String> EXCLUDED_JAR_TYPES = Collections
			.unmodifiableSet(new HashSet<>(Arrays.asList("annotation-processor", "dependencies-starter")));

	JarTypeFilter() {
		super(Collections.emptyList());
	}

	@Override
	protected boolean filter(Artifact artifact) {
		try (JarFile jarFile = new JarFile(artifact.getFile())) {
			String jarType = jarFile.getManifest().getMainAttributes().getValue("Spring-Boot-Jar-Type");
			return jarType != null && EXCLUDED_JAR_TYPES.contains(jarType);
		}
		catch (IOException ex) {
			return false;
		}
	}

}
