package org.springframework.boot.build.classpath;

import java.io.IOException;
import java.util.TreeSet;
import java.util.stream.Collectors;

import org.gradle.api.DefaultTask;
import org.gradle.api.GradleException;
import org.gradle.api.Task;
import org.gradle.api.artifacts.Configuration;
import org.gradle.api.artifacts.ModuleVersionIdentifier;
import org.gradle.api.file.FileCollection;
import org.gradle.api.tasks.Classpath;
import org.gradle.api.tasks.TaskAction;

/**
 * A {@link Task} for checking the classpath for prohibited dependencies.
 *

 */
public class CheckClasspathForProhibitedDependencies extends DefaultTask {

	private Configuration classpath;

	public CheckClasspathForProhibitedDependencies() {
		getOutputs().upToDateWhen((task) -> true);
	}

	public void setClasspath(Configuration classpath) {
		this.classpath = classpath;
	}

	@Classpath
	public FileCollection getClasspath() {
		return this.classpath;
	}

	@TaskAction
	public void checkForProhibitedDependencies() throws IOException {
		TreeSet<String> prohibited = this.classpath.getResolvedConfiguration().getResolvedArtifacts().stream()
				.map((artifact) -> artifact.getModuleVersion().getId()).filter(this::prohibited)
				.map((id) -> id.getGroup() + ":" + id.getName()).collect(Collectors.toCollection(TreeSet::new));
		if (!prohibited.isEmpty()) {
			StringBuilder message = new StringBuilder(String.format("Found prohibited dependencies:%n"));
			for (String dependency : prohibited) {
				message.append(String.format("    %s%n", dependency));
			}
			throw new GradleException(message.toString());
		}
	}

	private boolean prohibited(ModuleVersionIdentifier id) {
		String group = id.getGroup();
		if (group.equals("javax.batch")) {
			return false;
		}
		if (group.startsWith("javax")) {
			return true;
		}
		if (group.equals("commons-logging")) {
			return true;
		}
		if (group.equals("org.slf4j") && id.getName().equals("jcl-over-slf4j")) {
			return true;
		}
		return false;
	}

}
