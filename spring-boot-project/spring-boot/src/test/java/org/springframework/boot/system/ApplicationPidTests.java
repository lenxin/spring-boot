package org.springframework.boot.system;

import java.io.File;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatIllegalStateException;
import static org.assertj.core.api.Assertions.contentOf;

/**
 * Tests for {@link ApplicationPid}.
 *

 */
class ApplicationPidTests {

	@TempDir
	File tempDir;

	@Test
	void toStringWithPid() {
		assertThat(new ApplicationPid("123").toString()).isEqualTo("123");
	}

	@Test
	void toStringWithoutPid() {
		assertThat(new ApplicationPid(null).toString()).isEqualTo("???");
	}

	@Test
	void throwIllegalStateWritingMissingPid() {
		ApplicationPid pid = new ApplicationPid(null);
		assertThatIllegalStateException().isThrownBy(() -> pid.write(new File(this.tempDir, "pid")))
				.withMessageContaining("No PID available");
	}

	@Test
	void writePid() throws Exception {
		ApplicationPid pid = new ApplicationPid("123");
		File file = new File(this.tempDir, "pid");
		pid.write(file);
		assertThat(contentOf(file)).isEqualTo("123");
	}

	@Test
	void writeNewPid() throws Exception {
		// gh-10784
		ApplicationPid pid = new ApplicationPid("123");
		File file = new File(this.tempDir, "pid");
		file.delete();
		pid.write(file);
		assertThat(contentOf(file)).isEqualTo("123");
	}

	@Test
	void getPidFromJvm() {
		assertThat(new ApplicationPid().toString()).isNotEmpty();
	}

}
