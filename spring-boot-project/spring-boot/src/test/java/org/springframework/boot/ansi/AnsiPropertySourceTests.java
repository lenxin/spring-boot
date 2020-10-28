package org.springframework.boot.ansi;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import org.springframework.boot.ansi.AnsiOutput.Enabled;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Tests for {@link AnsiPropertySource}.
 *


 */
class AnsiPropertySourceTests {

	private AnsiPropertySource source = new AnsiPropertySource("ansi", false);

	@AfterEach
	void reset() {
		AnsiOutput.setEnabled(Enabled.DETECT);
	}

	@Test
	void getAnsiStyle() {
		assertThat(this.source.getProperty("AnsiStyle.BOLD")).isEqualTo(AnsiStyle.BOLD);
	}

	@Test
	void getAnsiColor() {
		assertThat(this.source.getProperty("AnsiColor.RED")).isEqualTo(AnsiColor.RED);
		assertThat(this.source.getProperty("AnsiColor.100")).isEqualTo(Ansi8BitColor.foreground(100));
	}

	@Test
	void getAnsiBackground() {
		assertThat(this.source.getProperty("AnsiBackground.GREEN")).isEqualTo(AnsiBackground.GREEN);
		assertThat(this.source.getProperty("AnsiBackground.100")).isEqualTo(Ansi8BitColor.background(100));
	}

	@Test
	void getAnsi() {
		assertThat(this.source.getProperty("Ansi.BOLD")).isEqualTo(AnsiStyle.BOLD);
		assertThat(this.source.getProperty("Ansi.RED")).isEqualTo(AnsiColor.RED);
		assertThat(this.source.getProperty("Ansi.BG_RED")).isEqualTo(AnsiBackground.RED);
	}

	@Test
	void getMissing() {
		assertThat(this.source.getProperty("AnsiStyle.NOPE")).isNull();
	}

	@Test
	void encodeEnabled() {
		AnsiOutput.setEnabled(Enabled.ALWAYS);
		AnsiPropertySource source = new AnsiPropertySource("ansi", true);
		assertThat(source.getProperty("Ansi.RED")).isEqualTo("\033[31m");
		assertThat(source.getProperty("AnsiColor.100")).isEqualTo("\033[38;5;100m");
		assertThat(source.getProperty("AnsiBackground.100")).isEqualTo("\033[48;5;100m");
	}

	@Test
	void encodeDisabled() {
		AnsiOutput.setEnabled(Enabled.NEVER);
		AnsiPropertySource source = new AnsiPropertySource("ansi", true);
		assertThat(source.getProperty("Ansi.RED")).isEqualTo("");
		assertThat(source.getProperty("AnsiColor.100")).isEqualTo("");
		assertThat(source.getProperty("AnsiBackground.100")).isEqualTo("");
	}

}
