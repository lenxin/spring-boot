package org.springframework.boot.build;

import org.asciidoctor.gradle.jvm.AsciidoctorJPlugin;
import org.gradle.api.Plugin;
import org.gradle.api.Project;
import org.gradle.api.plugins.JavaBasePlugin;
import org.gradle.api.publish.maven.plugins.MavenPublishPlugin;

/**
 * Plugin to apply conventions to projects that are part of Spring Boot's build.
 * Conventions are applied in response to various plugins being applied.
 *
 * When the {@link JavaBasePlugin} is applied, the conventions in {@link JavaConventions}
 * are applied.
 *
 * When the {@link MavenPublishPlugin} is applied, the conventions in
 * {@link MavenPublishingConventions} are applied.
 *
 * When the {@link AsciidoctorJPlugin} is applied, the conventions in
 * {@link AsciidoctorConventions} are applied.
 *



 */
public class ConventionsPlugin implements Plugin<Project> {

	@Override
	public void apply(Project project) {
		new JavaConventions().apply(project);
		new MavenPublishingConventions().apply(project);
		new AsciidoctorConventions().apply(project);
	}

}
