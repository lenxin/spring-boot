package org.springframework.boot.devtools.autoconfigure;

import java.io.File;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;

/**
 * Tests for {@link TriggerFileFilter}.
 *

 */
class TriggerFileFilterTests {

	@TempDir
	File tempDir;

	@Test
	void nameMustNotBeNull() {
		assertThatIllegalArgumentException().isThrownBy(() -> new TriggerFileFilter(null))
				.withMessageContaining("Name must not be null");
	}

	@Test
	void acceptNameMatch() throws Exception {
		File file = new File(this.tempDir, "thefile.txt");
		file.createNewFile();
		assertThat(new TriggerFileFilter("thefile.txt").accept(file)).isTrue();
	}

	@Test
	void doesNotAcceptNameMismatch() throws Exception {
		File file = new File(this.tempDir, "notthefile.txt");
		file.createNewFile();
		assertThat(new TriggerFileFilter("thefile.txt").accept(file)).isFalse();
	}

	@Test
	void testName() throws Exception {
		File file = new File(this.tempDir, ".triggerfile");
		file.createNewFile();
		assertThat(new TriggerFileFilter(".triggerfile").accept(file)).isTrue();
	}

}
