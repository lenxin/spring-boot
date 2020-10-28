package org.springframework.boot.web.embedded.undertow;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.jar.JarOutputStream;
import java.util.zip.ZipEntry;

import io.undertow.server.handlers.resource.Resource;
import io.undertow.server.handlers.resource.ResourceManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Tests for {@link JarResourceManager}.
 *

 */
class JarResourceManagerTests {

	private ResourceManager resourceManager;

	@BeforeEach
	void createJar(@TempDir File tempDir) throws IOException {
		File jar = new File(tempDir, "test.jar");
		try (JarOutputStream out = new JarOutputStream(new FileOutputStream(jar))) {
			out.putNextEntry(new ZipEntry("hello.txt"));
			out.write("hello".getBytes());
		}
		this.resourceManager = new JarResourceManager(jar);
	}

	@Test
	void emptyPathIsHandledCorrectly() throws IOException {
		Resource resource = this.resourceManager.getResource("");
		assertThat(resource).isNotNull();
		assertThat(resource.isDirectory()).isTrue();
	}

	@Test
	void rootPathIsHandledCorrectly() throws IOException {
		Resource resource = this.resourceManager.getResource("/");
		assertThat(resource).isNotNull();
		assertThat(resource.isDirectory()).isTrue();
	}

	@Test
	void resourceIsFoundInJarFile() throws IOException {
		Resource resource = this.resourceManager.getResource("/hello.txt");
		assertThat(resource).isNotNull();
		assertThat(resource.isDirectory()).isFalse();
		assertThat(resource.getContentLength()).isEqualTo(5);
	}

	@Test
	void resourceIsFoundInJarFileWithoutLeadingSlash() throws IOException {
		Resource resource = this.resourceManager.getResource("hello.txt");
		assertThat(resource).isNotNull();
		assertThat(resource.isDirectory()).isFalse();
		assertThat(resource.getContentLength()).isEqualTo(5);
	}

}
