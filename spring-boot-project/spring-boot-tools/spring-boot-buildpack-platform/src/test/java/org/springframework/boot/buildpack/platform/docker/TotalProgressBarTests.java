package org.springframework.boot.buildpack.platform.docker;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Tests for {@link TotalProgressBar}.
 *

 */
class TotalProgressBarTests {

	@Test
	void withPrefixAndBookends() {
		TestPrintStream out = new TestPrintStream();
		TotalProgressBar bar = new TotalProgressBar("prefix:", '#', true, out);
		assertThat(out).hasToString("prefix: [ ");
		bar.accept(new TotalProgressEvent(10));
		assertThat(out.toString()).isEqualTo("prefix: [ #####");
		bar.accept(new TotalProgressEvent(50));
		assertThat(out.toString()).isEqualTo("prefix: [ #########################");
		bar.accept(new TotalProgressEvent(100));
		assertThat(out.toString())
				.isEqualTo(String.format("prefix: [ ################################################## ]%n"));
	}

	@Test
	void withoutPrefix() {
		TestPrintStream out = new TestPrintStream();
		TotalProgressBar bar = new TotalProgressBar(null, '#', true, out);
		assertThat(out).hasToString("[ ");
		bar.accept(new TotalProgressEvent(10));
		assertThat(out.toString()).isEqualTo("[ #####");
		bar.accept(new TotalProgressEvent(50));
		assertThat(out.toString()).isEqualTo("[ #########################");
		bar.accept(new TotalProgressEvent(100));
		assertThat(out.toString()).isEqualTo(String.format("[ ################################################## ]%n"));
	}

	@Test
	void withoutBookends() {
		TestPrintStream out = new TestPrintStream();
		TotalProgressBar bar = new TotalProgressBar("", '.', false, out);
		assertThat(out).hasToString("");
		bar.accept(new TotalProgressEvent(10));
		assertThat(out.toString()).isEqualTo(".....");
		bar.accept(new TotalProgressEvent(50));
		assertThat(out.toString()).isEqualTo(".........................");
		bar.accept(new TotalProgressEvent(100));
		assertThat(out.toString()).isEqualTo(String.format("..................................................%n"));
	}

	static class TestPrintStream extends PrintStream {

		TestPrintStream() {
			super(new ByteArrayOutputStream());
		}

		@Override
		public String toString() {
			return this.out.toString();
		}

	}

}
