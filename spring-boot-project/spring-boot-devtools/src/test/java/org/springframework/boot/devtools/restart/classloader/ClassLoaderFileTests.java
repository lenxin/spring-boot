package org.springframework.boot.devtools.restart.classloader;

import org.junit.jupiter.api.Test;

import org.springframework.boot.devtools.restart.classloader.ClassLoaderFile.Kind;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;

/**
 * Tests for {@link ClassLoaderFile}.
 *

 */
class ClassLoaderFileTests {

	public static final byte[] BYTES = "ABC".getBytes();

	@Test
	void kindMustNotBeNull() {
		assertThatIllegalArgumentException().isThrownBy(() -> new ClassLoaderFile(null, null))
				.withMessageContaining("Kind must not be null");
	}

	@Test
	void addedContentsMustNotBeNull() {
		assertThatIllegalArgumentException().isThrownBy(() -> new ClassLoaderFile(Kind.ADDED, null))
				.withMessageContaining("Contents must not be null");
	}

	@Test
	void modifiedContentsMustNotBeNull() {
		assertThatIllegalArgumentException().isThrownBy(() -> new ClassLoaderFile(Kind.MODIFIED, null))
				.withMessageContaining("Contents must not be null");
	}

	@Test
	void deletedContentsMustBeNull() {
		assertThatIllegalArgumentException().isThrownBy(() -> new ClassLoaderFile(Kind.DELETED, new byte[10]))
				.withMessageContaining("Contents must be null");
	}

	@Test
	void added() {
		ClassLoaderFile file = new ClassLoaderFile(Kind.ADDED, BYTES);
		assertThat(file.getKind()).isEqualTo(ClassLoaderFile.Kind.ADDED);
		assertThat(file.getContents()).isEqualTo(BYTES);
	}

	@Test
	void modified() {
		ClassLoaderFile file = new ClassLoaderFile(Kind.MODIFIED, BYTES);
		assertThat(file.getKind()).isEqualTo(ClassLoaderFile.Kind.MODIFIED);
		assertThat(file.getContents()).isEqualTo(BYTES);
	}

	@Test
	void deleted() {
		ClassLoaderFile file = new ClassLoaderFile(Kind.DELETED, null);
		assertThat(file.getKind()).isEqualTo(ClassLoaderFile.Kind.DELETED);
		assertThat(file.getContents()).isNull();
	}

}
