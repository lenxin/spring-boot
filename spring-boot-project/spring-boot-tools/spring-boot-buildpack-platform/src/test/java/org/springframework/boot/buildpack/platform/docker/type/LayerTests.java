package org.springframework.boot.buildpack.platform.docker.type;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;

import org.apache.commons.compress.archivers.tar.TarArchiveInputStream;
import org.junit.jupiter.api.Test;

import org.springframework.boot.buildpack.platform.io.Content;
import org.springframework.boot.buildpack.platform.io.IOConsumer;
import org.springframework.boot.buildpack.platform.io.Layout;
import org.springframework.boot.buildpack.platform.io.Owner;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;

/**
 * Tests for {@link Layer}.
 *

 */
class LayerTests {

	@Test
	void ofWhenLayoutIsNullThrowsException() {
		assertThatIllegalArgumentException().isThrownBy(() -> Layer.of((IOConsumer<Layout>) null))
				.withMessage("Layout must not be null");
	}

	@Test
	void fromTarArchiveWhenTarArchiveIsNullThrowsException() {
		assertThatIllegalArgumentException().isThrownBy(() -> Layer.fromTarArchive(null))
				.withMessage("TarArchive must not be null");
	}

	@Test
	void ofCreatesLayer() throws Exception {
		Layer layer = Layer.of((layout) -> {
			layout.directory("/directory", Owner.ROOT);
			layout.file("/directory/file", Owner.ROOT, Content.of("test"));
		});
		assertThat(layer.getId().toString())
				.isEqualTo("sha256:d03a34f73804698c875eb56ff694fc2fceccc69b645e4adceb004ed13588613b");
		ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
		layer.writeTo(outputStream);
		try (TarArchiveInputStream tarStream = new TarArchiveInputStream(
				new ByteArrayInputStream(outputStream.toByteArray()))) {
			assertThat(tarStream.getNextTarEntry().getName()).isEqualTo("/directory/");
			assertThat(tarStream.getNextTarEntry().getName()).isEqualTo("/directory/file");
			assertThat(tarStream.getNextTarEntry()).isNull();
		}
	}

}
