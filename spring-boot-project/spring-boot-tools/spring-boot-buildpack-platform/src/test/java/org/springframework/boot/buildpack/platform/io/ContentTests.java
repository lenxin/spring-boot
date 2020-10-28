package org.springframework.boot.buildpack.platform.io;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;

/**
 * Tests for {@link Content}.
 *

 */
class ContentTests {

	@Test
	void ofWhenStreamIsNullThrowsException() {
		assertThatIllegalArgumentException().isThrownBy(() -> Content.of(1, (IOSupplier<InputStream>) null))
				.withMessage("Supplier must not be null");
	}

	@Test
	void ofWhenStreamReturnsWritable() throws Exception {
		byte[] bytes = { 1, 2, 3, 4 };
		ByteArrayInputStream inputStream = new ByteArrayInputStream(bytes);
		Content writable = Content.of(4, () -> inputStream);
		assertThat(writeToAndGetBytes(writable)).isEqualTo(bytes);
	}

	@Test
	void ofWhenStringIsNullThrowsException() {
		assertThatIllegalArgumentException().isThrownBy(() -> Content.of((String) null))
				.withMessage("String must not be null");
	}

	@Test
	void ofWhenStringReturnsWritable() throws Exception {
		Content writable = Content.of("spring");
		assertThat(writeToAndGetBytes(writable)).isEqualTo("spring".getBytes(StandardCharsets.UTF_8));
	}

	@Test
	void ofWhenBytesIsNullThrowsException() {
		assertThatIllegalArgumentException().isThrownBy(() -> Content.of((byte[]) null))
				.withMessage("Bytes must not be null");
	}

	@Test
	void ofWhenBytesReturnsWritable() throws Exception {
		byte[] bytes = { 1, 2, 3, 4 };
		Content writable = Content.of(bytes);
		assertThat(writeToAndGetBytes(writable)).isEqualTo(bytes);
	}

	private byte[] writeToAndGetBytes(Content writable) throws IOException {
		ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
		writable.writeTo(outputStream);
		return outputStream.toByteArray();
	}

}
