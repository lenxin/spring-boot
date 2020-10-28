package org.springframework.boot.cli.util;

import java.io.File;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.List;

import org.junit.jupiter.api.Test;

import org.springframework.util.ClassUtils;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Tests for {@link ResourceUtils}.
 *

 */
class ResourceUtilsTests {

	@Test
	void explicitClasspathResource() {
		List<String> urls = ResourceUtils.getUrls("classpath:init.groovy", ClassUtils.getDefaultClassLoader());
		assertThat(urls).hasSize(1);
		assertThat(urls.get(0).startsWith("file:")).isTrue();
	}

	@Test
	void duplicateResource() throws Exception {
		URLClassLoader loader = new URLClassLoader(new URL[] { new URL("file:./src/test/resources/"),
				new File("src/test/resources/").getAbsoluteFile().toURI().toURL() });
		List<String> urls = ResourceUtils.getUrls("classpath:init.groovy", loader);
		assertThat(urls).hasSize(1);
		assertThat(urls.get(0).startsWith("file:")).isTrue();
	}

	@Test
	void explicitClasspathResourceWithSlash() {
		List<String> urls = ResourceUtils.getUrls("classpath:/init.groovy", ClassUtils.getDefaultClassLoader());
		assertThat(urls).hasSize(1);
		assertThat(urls.get(0).startsWith("file:")).isTrue();
	}

	@Test
	void implicitClasspathResource() {
		List<String> urls = ResourceUtils.getUrls("init.groovy", ClassUtils.getDefaultClassLoader());
		assertThat(urls).hasSize(1);
		assertThat(urls.get(0).startsWith("file:")).isTrue();
	}

	@Test
	void implicitClasspathResourceWithSlash() {
		List<String> urls = ResourceUtils.getUrls("/init.groovy", ClassUtils.getDefaultClassLoader());
		assertThat(urls).hasSize(1);
		assertThat(urls.get(0).startsWith("file:")).isTrue();
	}

	@Test
	void nonexistentClasspathResource() {
		List<String> urls = ResourceUtils.getUrls("classpath:nonexistent.groovy", null);
		assertThat(urls).isEmpty();
	}

	@Test
	void explicitFile() {
		List<String> urls = ResourceUtils.getUrls("file:src/test/resources/init.groovy",
				ClassUtils.getDefaultClassLoader());
		assertThat(urls).hasSize(1);
		assertThat(urls.get(0).startsWith("file:")).isTrue();
	}

	@Test
	void implicitFile() {
		List<String> urls = ResourceUtils.getUrls("src/test/resources/init.groovy", ClassUtils.getDefaultClassLoader());
		assertThat(urls).hasSize(1);
		assertThat(urls.get(0).startsWith("file:")).isTrue();
	}

	@Test
	void nonexistentFile() {
		List<String> urls = ResourceUtils.getUrls("file:nonexistent.groovy", null);
		assertThat(urls).isEmpty();
	}

	@Test
	void recursiveFiles() {
		List<String> urls = ResourceUtils.getUrls("src/test/resources/dir-sample", ClassUtils.getDefaultClassLoader());
		assertThat(urls).hasSize(1);
		assertThat(urls.get(0).startsWith("file:")).isTrue();
	}

	@Test
	void recursiveFilesByPatternWithPrefix() {
		List<String> urls = ResourceUtils.getUrls("file:src/test/resources/dir-sample/**/*.groovy",
				ClassUtils.getDefaultClassLoader());
		assertThat(urls).hasSize(1);
		assertThat(urls.get(0).startsWith("file:")).isTrue();
	}

	@Test
	void recursiveFilesByPattern() {
		List<String> urls = ResourceUtils.getUrls("src/test/resources/dir-sample/**/*.groovy",
				ClassUtils.getDefaultClassLoader());
		assertThat(urls).hasSize(1);
		assertThat(urls.get(0).startsWith("file:")).isTrue();
	}

	@Test
	void directoryOfFilesWithPrefix() {
		List<String> urls = ResourceUtils.getUrls("file:src/test/resources/dir-sample/code/*",
				ClassUtils.getDefaultClassLoader());
		assertThat(urls).hasSize(1);
		assertThat(urls.get(0).startsWith("file:")).isTrue();
	}

	@Test
	void directoryOfFiles() {
		List<String> urls = ResourceUtils.getUrls("src/test/resources/dir-sample/code/*",
				ClassUtils.getDefaultClassLoader());
		assertThat(urls).hasSize(1);
		assertThat(urls.get(0).startsWith("file:")).isTrue();
	}

}
