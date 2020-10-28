package org.springframework.boot.devtools.filewatch;

import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import org.springframework.util.FileCopyUtils;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;

/**
 * Tests for {@link FileSnapshot}.
 *

 */
class FileSnapshotTests {

	private static final long TWO_MINS = TimeUnit.MINUTES.toMillis(2);

	private static final long MODIFIED = new Date().getTime() - TimeUnit.DAYS.toMillis(10);

	@TempDir
	File tempDir;

	@Test
	void fileMustNotBeNull() {
		assertThatIllegalArgumentException().isThrownBy(() -> new FileSnapshot(null))
				.withMessageContaining("File must not be null");
	}

	@Test
	void fileMustNotBeADirectory() throws Exception {
		File file = new File(this.tempDir, "file");
		file.mkdir();
		assertThatIllegalArgumentException().isThrownBy(() -> new FileSnapshot(file))
				.withMessageContaining("File must not be a directory");
	}

	@Test
	void equalsIfTheSame() throws Exception {
		File file = createNewFile("abc", MODIFIED);
		File fileCopy = new File(file, "x").getParentFile();
		FileSnapshot snapshot1 = new FileSnapshot(file);
		FileSnapshot snapshot2 = new FileSnapshot(fileCopy);
		assertThat(snapshot1).isEqualTo(snapshot2);
		assertThat(snapshot1.hashCode()).isEqualTo(snapshot2.hashCode());
	}

	@Test
	void notEqualsIfDeleted() throws Exception {
		File file = createNewFile("abc", MODIFIED);
		FileSnapshot snapshot1 = new FileSnapshot(file);
		file.delete();
		assertThat(snapshot1).isNotEqualTo(new FileSnapshot(file));
	}

	@Test
	void notEqualsIfLengthChanges() throws Exception {
		File file = createNewFile("abc", MODIFIED);
		FileSnapshot snapshot1 = new FileSnapshot(file);
		setupFile(file, "abcd", MODIFIED);
		assertThat(snapshot1).isNotEqualTo(new FileSnapshot(file));
	}

	@Test
	void notEqualsIfLastModifiedChanges() throws Exception {
		File file = createNewFile("abc", MODIFIED);
		FileSnapshot snapshot1 = new FileSnapshot(file);
		setupFile(file, "abc", MODIFIED + TWO_MINS);
		assertThat(snapshot1).isNotEqualTo(new FileSnapshot(file));
	}

	private File createNewFile(String content, long lastModified) throws IOException {
		File file = new File(this.tempDir, UUID.randomUUID().toString());
		setupFile(file, content, lastModified);
		return file;
	}

	private void setupFile(File file, String content, long lastModified) throws IOException {
		FileCopyUtils.copy(content.getBytes(), file);
		file.setLastModified(lastModified);
	}

}
