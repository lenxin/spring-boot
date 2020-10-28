package org.springframework.boot.loader.tools;

import java.io.ByteArrayInputStream;
import java.io.IOException;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Tests for {@link ZipHeaderPeekInputStream}.
 *

 */
class ZipHeaderPeekInputStreamTests {

	@Test
	void hasZipHeaderReturnsTrueWhenStreamStartsWithZipHeader() throws IOException {
		try (ZipHeaderPeekInputStream in = new ZipHeaderPeekInputStream(
				new ByteArrayInputStream(new byte[] { 0x50, 0x4b, 0x03, 0x04, 5, 6 }))) {
			assertThat(in.hasZipHeader()).isTrue();
		}
	}

	@Test
	void hasZipHeaderReturnsFalseWhenStreamDoesNotStartWithZipHeader() throws IOException {
		try (ZipHeaderPeekInputStream in = new ZipHeaderPeekInputStream(
				new ByteArrayInputStream(new byte[] { 0, 1, 2, 3, 4, 5 }))) {
			assertThat(in.hasZipHeader()).isFalse();
		}
	}

	@Test
	void readIndividualBytes() throws IOException {
		try (ZipHeaderPeekInputStream in = new ZipHeaderPeekInputStream(
				new ByteArrayInputStream(new byte[] { 0, 1, 2, 3, 4, 5 }))) {
			assertThat(in.read()).isEqualTo(0);
			assertThat(in.read()).isEqualTo(1);
			assertThat(in.read()).isEqualTo(2);
			assertThat(in.read()).isEqualTo(3);
			assertThat(in.read()).isEqualTo(4);
			assertThat(in.read()).isEqualTo(5);
		}
	}

	@Test
	void readMultipleBytes() throws IOException {
		try (ZipHeaderPeekInputStream in = new ZipHeaderPeekInputStream(
				new ByteArrayInputStream(new byte[] { 0, 1, 2, 3, 4, 5 }))) {
			byte[] bytes = new byte[3];
			assertThat(in.read(bytes)).isEqualTo(3);
			assertThat(bytes).containsExactly(0, 1, 2);
			assertThat(in.read(bytes)).isEqualTo(3);
			assertThat(bytes).containsExactly(3, 4, 5);
			assertThat(in.read(bytes)).isEqualTo(-1);
		}
	}

	@Test
	void readingMoreThanEntireStreamReadsToEndOfStream() throws IOException {
		try (ZipHeaderPeekInputStream in = new ZipHeaderPeekInputStream(
				new ByteArrayInputStream(new byte[] { 0, 1, 2, 3, 4, 5 }))) {
			byte[] bytes = new byte[8];
			assertThat(in.read(bytes)).isEqualTo(6);
			assertThat(bytes).containsExactly(0, 1, 2, 3, 4, 5, 0, 0);
			assertThat(in.read(bytes)).isEqualTo(-1);
		}
	}

	@Test
	void readOfSomeOfTheHeaderThenMoreThanEntireStreamReadsToEndOfStream() throws IOException {
		try (ZipHeaderPeekInputStream in = new ZipHeaderPeekInputStream(
				new ByteArrayInputStream(new byte[] { 0, 1, 2, 3, 4, 5 }))) {
			byte[] bytes = new byte[8];
			assertThat(in.read(bytes, 0, 3)).isEqualTo(3);
			assertThat(bytes).containsExactly(0, 1, 2, 0, 0, 0, 0, 0);
			assertThat(in.read(bytes, 3, 5)).isEqualTo(3);
			assertThat(bytes).containsExactly(0, 1, 2, 3, 4, 5, 0, 0);
		}
	}

	@Test
	void readMoreThanEntireStreamWhenStreamLengthIsLessThanZipHeaderLength() throws IOException {
		try (ZipHeaderPeekInputStream in = new ZipHeaderPeekInputStream(new ByteArrayInputStream(new byte[] { 10 }))) {
			byte[] bytes = new byte[8];
			assertThat(in.read(bytes)).isEqualTo(1);
			assertThat(bytes).containsExactly(10, 0, 0, 0, 0, 0, 0, 0);
		}
	}

	@Test
	void readMoreThanEntireStreamWhenStreamLengthIsSameAsHeaderLength() throws IOException {
		try (ZipHeaderPeekInputStream in = new ZipHeaderPeekInputStream(
				new ByteArrayInputStream(new byte[] { 1, 2, 3, 4 }))) {
			byte[] bytes = new byte[8];
			assertThat(in.read(bytes)).isEqualTo(4);
			assertThat(bytes).containsExactly(1, 2, 3, 4, 0, 0, 0, 0);
		}
	}

	@Test
	void readMoreThanEntireStreamWhenStreamLengthIsZero() throws IOException {
		try (ZipHeaderPeekInputStream in = new ZipHeaderPeekInputStream(new ByteArrayInputStream(new byte[0]))) {
			byte[] bytes = new byte[8];
			assertThat(in.read(bytes)).isEqualTo(-1);
			assertThat(bytes).containsExactly(0, 0, 0, 0, 0, 0, 0, 0);
		}
	}

}
