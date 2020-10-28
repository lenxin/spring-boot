package org.springframework.boot.gradle.plugin;

import java.io.File;
import java.util.Collections;
import java.util.Set;
import java.util.jar.JarFile;

import org.gradle.api.file.FileCollection;
import org.gradle.api.specs.Spec;

/**
 * A {@link Spec} for {@link FileCollection#filter(Spec) filtering} {@code FileCollection}
 * to remove jar files based on their {@code Spring-Boot-Jar-Type} as defined in the
 * manifest. Jars of type {@code dependencies-starter} are excluded.
 *

 */
class JarTypeFileSpec implements Spec<File> {

	private static final Set<String> EXCLUDED_JAR_TYPES = Collections.singleton("dependencies-starter");

	@Override
	public boolean isSatisfiedBy(File file) {
		try (JarFile jar = new JarFile(file)) {
			String jarType = jar.getManifest().getMainAttributes().getValue("Spring-Boot-Jar-Type");
			if (jarType != null && EXCLUDED_JAR_TYPES.contains(jarType)) {
				return false;
			}
		}
		catch (Exception ex) {
			// Continue
		}
		return true;
	}

}
