package org.springframework.boot.loader.tools;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Tests for {@link FileUtils}.
 *


 */
class FileUtilsTests {

	@TempDir
	File tempDir;

	private File outputDirectory;

	private File originDirectory;

	@BeforeEach
	void init() throws IOException {
		this.outputDirectory = new File(this.tempDir, "remove");
		this.originDirectory = new File(this.tempDir, "keep");
		this.outputDirectory.mkdirs();
		this.originDirectory.mkdirs();
	}

	@Test
	void simpleDuplicateFile() throws IOException {
		File file = new File(this.outputDirectory, "logback.xml");
		file.createNewFile();
		new File(this.originDirectory, "logback.xml").createNewFile();
		FileUtils.removeDuplicatesFromOutputDirectory(this.outputDirectory, this.originDirectory);
		assertThat(file.exists()).isFalse();
	}

	@Test
	void nestedDuplicateFile() throws IOException {
		assertThat(new File(this.outputDirectory, "sub").mkdirs()).isTrue();
		assertThat(new File(this.originDirectory, "sub").mkdirs()).isTrue();
		File file = new File(this.outputDirectory, "sub/logback.xml");
		file.createNewFile();
		new File(this.originDirectory, "sub/logback.xml").createNewFile();
		FileUtils.removeDuplicatesFromOutputDirectory(this.outputDirectory, this.originDirectory);
		assertThat(file.exists()).isFalse();
	}

	@Test
	void nestedNonDuplicateFile() throws IOException {
		assertThat(new File(this.outputDirectory, "sub").mkdirs()).isTrue();
		assertThat(new File(this.originDirectory, "sub").mkdirs()).isTrue();
		File file = new File(this.outputDirectory, "sub/logback.xml");
		file.createNewFile();
		new File(this.originDirectory, "sub/different.xml").createNewFile();
		FileUtils.removeDuplicatesFromOutputDirectory(this.outputDirectory, this.originDirectory);
		assertThat(file.exists()).isTrue();
	}

	@Test
	void nonDuplicateFile() throws IOException {
		File file = new File(this.outputDirectory, "logback.xml");
		file.createNewFile();
		new File(this.originDirectory, "different.xml").createNewFile();
		FileUtils.removeDuplicatesFromOutputDirectory(this.outputDirectory, this.originDirectory);
		assertThat(file.exists()).isTrue();
	}

	@Test
	void hash() throws Exception {
		File file = new File(this.tempDir, "file");
		try (OutputStream outputStream = new FileOutputStream(file)) {
			outputStream.write(new byte[] { 1, 2, 3 });
		}
		assertThat(FileUtils.sha1Hash(file)).isEqualTo("7037807198c22a7d2b0807371d763779a84fdfcf");
	}

}
