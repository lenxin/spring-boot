package org.springframework.boot.loader;

import java.io.File;
import java.io.InputStream;
import java.net.JarURLConnection;
import java.net.URL;
import java.net.URLConnection;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import org.springframework.boot.loader.jar.JarFile;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Tests for {@link LaunchedURLClassLoader}.
 *



 */
@SuppressWarnings("resource")
class LaunchedURLClassLoaderTests {

	@TempDir
	File tempDir;

	@Test
	void resolveResourceFromArchive() throws Exception {
		LaunchedURLClassLoader loader = new LaunchedURLClassLoader(
				new URL[] { new URL("jar:file:src/test/resources/jars/app.jar!/") }, getClass().getClassLoader());
		assertThat(loader.getResource("demo/Application.java")).isNotNull();
	}

	@Test
	void resolveResourcesFromArchive() throws Exception {
		LaunchedURLClassLoader loader = new LaunchedURLClassLoader(
				new URL[] { new URL("jar:file:src/test/resources/jars/app.jar!/") }, getClass().getClassLoader());
		assertThat(loader.getResources("demo/Application.java").hasMoreElements()).isTrue();
	}

	@Test
	void resolveRootPathFromArchive() throws Exception {
		LaunchedURLClassLoader loader = new LaunchedURLClassLoader(
				new URL[] { new URL("jar:file:src/test/resources/jars/app.jar!/") }, getClass().getClassLoader());
		assertThat(loader.getResource("")).isNotNull();
	}

	@Test
	void resolveRootResourcesFromArchive() throws Exception {
		LaunchedURLClassLoader loader = new LaunchedURLClassLoader(
				new URL[] { new URL("jar:file:src/test/resources/jars/app.jar!/") }, getClass().getClassLoader());
		assertThat(loader.getResources("").hasMoreElements()).isTrue();
	}

	@Test
	void resolveFromNested() throws Exception {
		File file = new File(this.tempDir, "test.jar");
		TestJarCreator.createTestJar(file);
		try (JarFile jarFile = new JarFile(file)) {
			URL url = jarFile.getUrl();
			try (LaunchedURLClassLoader loader = new LaunchedURLClassLoader(new URL[] { url }, null)) {
				URL resource = loader.getResource("nested.jar!/3.dat");
				assertThat(resource.toString()).isEqualTo(url + "nested.jar!/3.dat");
				try (InputStream input = resource.openConnection().getInputStream()) {
					assertThat(input.read()).isEqualTo(3);
				}
			}
		}
	}

	@Test
	void resolveFromNestedWhileThreadIsInterrupted() throws Exception {
		File file = new File(this.tempDir, "test.jar");
		TestJarCreator.createTestJar(file);
		try (JarFile jarFile = new JarFile(file)) {
			URL url = jarFile.getUrl();
			try (LaunchedURLClassLoader loader = new LaunchedURLClassLoader(new URL[] { url }, null)) {
				Thread.currentThread().interrupt();
				URL resource = loader.getResource("nested.jar!/3.dat");
				assertThat(resource.toString()).isEqualTo(url + "nested.jar!/3.dat");
				URLConnection connection = resource.openConnection();
				try (InputStream input = connection.getInputStream()) {
					assertThat(input.read()).isEqualTo(3);
				}
				((JarURLConnection) connection).getJarFile().close();
			}
			finally {
				Thread.interrupted();
			}
		}
	}

}
