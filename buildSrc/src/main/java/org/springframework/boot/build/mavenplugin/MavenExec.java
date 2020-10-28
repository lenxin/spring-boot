package org.springframework.boot.build.mavenplugin;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import org.gradle.api.Project;
import org.gradle.api.Task;
import org.gradle.api.artifacts.Configuration;
import org.gradle.api.tasks.Internal;
import org.gradle.api.tasks.JavaExec;
import org.gradle.api.tasks.PathSensitivity;
import org.gradle.api.tasks.TaskExecutionException;
import org.gradle.process.internal.ExecException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * A custom {@link JavaExec} {@link Task task} for running Maven.
 *

 */
public class MavenExec extends JavaExec {

	private final Logger log = LoggerFactory.getLogger(MavenExec.class);

	private File projectDir;

	public MavenExec() throws IOException {
		setClasspath(mavenConfiguration(getProject()));
		args("--batch-mode");
		setMain("org.apache.maven.cli.MavenCli");
	}

	public void setProjectDir(File projectDir) {
		this.projectDir = projectDir;
		getInputs().file(new File(projectDir, "pom.xml")).withPathSensitivity(PathSensitivity.RELATIVE);
	}

	@Override
	public void exec() {
		workingDir(this.projectDir);
		systemProperty("maven.multiModuleProjectDirectory", this.projectDir.getAbsolutePath());
		try {
			Path logFile = Files.createTempFile(getName(), ".log");
			try {
				args("--log-file", logFile.toFile().getAbsolutePath());
				super.exec();
				if (this.log.isInfoEnabled()) {
					Files.readAllLines(logFile).forEach(this.log::info);
				}
			}
			catch (ExecException ex) {
				System.out.println("Exec exception! Dumping log");
				Files.readAllLines(logFile).forEach(System.out::println);
				throw ex;
			}
		}
		catch (IOException ex) {
			throw new TaskExecutionException(this, ex);
		}
	}

	private Configuration mavenConfiguration(Project project) {
		Configuration existing = project.getConfigurations().findByName("maven");
		if (existing != null) {
			return existing;
		}
		return project.getConfigurations().create("maven", (maven) -> {
			maven.getDependencies().add(project.getDependencies().create("org.apache.maven:maven-embedder:3.6.2"));
			maven.getDependencies().add(project.getDependencies().create("org.apache.maven:maven-compat:3.6.2"));
			maven.getDependencies().add(project.getDependencies().create("org.slf4j:slf4j-simple:1.7.5"));
			maven.getDependencies().add(
					project.getDependencies().create("org.apache.maven.resolver:maven-resolver-connector-basic:1.4.1"));
			maven.getDependencies().add(
					project.getDependencies().create("org.apache.maven.resolver:maven-resolver-transport-http:1.4.1"));
		});
	}

	@Internal
	public File getProjectDir() {
		return this.projectDir;
	}

}
