package org.springframework.boot.web.servlet.server;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.function.Consumer;
import java.util.jar.JarEntry;
import java.util.jar.JarOutputStream;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Tests for {@link StaticResourceJars}.
 *


 */
class StaticResourceJarsTests {

	@TempDir
	File tempDir;

	@Test
	void includeJarWithStaticResources() throws Exception {
		File jarFile = createResourcesJar("test-resources.jar");
		List<URL> staticResourceJarUrls = new StaticResourceJars().getUrlsFrom(jarFile.toURI().toURL());
		assertThat(staticResourceJarUrls).hasSize(1);
	}

	@Test
	void includeJarWithStaticResourcesWithUrlEncodedSpaces() throws Exception {
		File jarFile = createResourcesJar("test resources.jar");
		List<URL> staticResourceJarUrls = new StaticResourceJars().getUrlsFrom(jarFile.toURI().toURL());
		assertThat(staticResourceJarUrls).hasSize(1);
	}

	@Test
	void includeJarWithStaticResourcesWithPlusInItsPath() throws Exception {
		File jarFile = createResourcesJar("test + resources.jar");
		List<URL> staticResourceJarUrls = new StaticResourceJars().getUrlsFrom(jarFile.toURI().toURL());
		assertThat(staticResourceJarUrls).hasSize(1);
	}

	@Test
	void excludeJarWithoutStaticResources() throws Exception {
		File jarFile = createJar("dependency.jar");
		List<URL> staticResourceJarUrls = new StaticResourceJars().getUrlsFrom(jarFile.toURI().toURL());
		assertThat(staticResourceJarUrls).hasSize(0);
	}

	@Test
	void uncPathsAreTolerated() throws Exception {
		File jarFile = createResourcesJar("test-resources.jar");
		List<URL> staticResourceJarUrls = new StaticResourceJars().getUrlsFrom(jarFile.toURI().toURL(),
				new URL("file://unc.example.com/test.jar"));
		assertThat(staticResourceJarUrls).hasSize(1);
	}

	@Test
	void ignoreWildcardUrls() throws Exception {
		File jarFile = createResourcesJar("test-resources.jar");
		URL folderUrl = jarFile.getParentFile().toURI().toURL();
		URL wildcardUrl = new URL(folderUrl.toString() + "*.jar");
		List<URL> staticResourceJarUrls = new StaticResourceJars().getUrlsFrom(wildcardUrl);
		assertThat(staticResourceJarUrls).isEmpty();
	}

	private File createResourcesJar(String name) throws IOException {
		return createJar(name, (output) -> {
			JarEntry jarEntry = new JarEntry("META-INF/resources");
			try {
				output.putNextEntry(jarEntry);
				output.closeEntry();
			}
			catch (IOException ex) {
				throw new RuntimeException(ex);
			}
		});
	}

	private File createJar(String name) throws IOException {
		return createJar(name, null);
	}

	private File createJar(String name, Consumer<JarOutputStream> customizer) throws IOException {
		File jarFile = new File(this.tempDir, name);
		JarOutputStream jarOutputStream = new JarOutputStream(new FileOutputStream(jarFile));
		if (customizer != null) {
			customizer.accept(jarOutputStream);
		}
		jarOutputStream.close();
		return jarFile;
	}

}
