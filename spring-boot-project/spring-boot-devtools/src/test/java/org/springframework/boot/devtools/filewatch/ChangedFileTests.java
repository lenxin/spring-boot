package org.springframework.boot.devtools.filewatch;

import java.io.File;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import org.springframework.boot.devtools.filewatch.ChangedFile.Type;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;

/**
 * Tests for {@link ChangedFile}.
 *

 */
class ChangedFileTests {

	@TempDir
	File tempDir;

	@Test
	void sourceDirectoryMustNotBeNull() throws Exception {
		assertThatIllegalArgumentException()
				.isThrownBy(() -> new ChangedFile(null, new File(this.tempDir, "file"), Type.ADD))
				.withMessageContaining("SourceDirectory must not be null");
	}

	@Test
	void fileMustNotBeNull() throws Exception {
		assertThatIllegalArgumentException()
				.isThrownBy(() -> new ChangedFile(new File(this.tempDir, "directory"), null, Type.ADD))
				.withMessageContaining("File must not be null");
	}

	@Test
	void typeMustNotBeNull() throws Exception {
		assertThatIllegalArgumentException().isThrownBy(
				() -> new ChangedFile(new File(this.tempDir, "file"), new File(this.tempDir, "directory"), null))
				.withMessageContaining("Type must not be null");
	}

	@Test
	void getFile() throws Exception {
		File file = new File(this.tempDir, "file");
		ChangedFile changedFile = new ChangedFile(new File(this.tempDir, "directory"), file, Type.ADD);
		assertThat(changedFile.getFile()).isEqualTo(file);
	}

	@Test
	void getType() throws Exception {
		ChangedFile changedFile = new ChangedFile(new File(this.tempDir, "directory"), new File(this.tempDir, "file"),
				Type.DELETE);
		assertThat(changedFile.getType()).isEqualTo(Type.DELETE);
	}

	@Test
	void getRelativeName() throws Exception {
		File subDirectory = new File(this.tempDir, "A");
		File file = new File(subDirectory, "B.txt");
		ChangedFile changedFile = new ChangedFile(this.tempDir, file, Type.ADD);
		assertThat(changedFile.getRelativeName()).isEqualTo("A/B.txt");
	}

}
