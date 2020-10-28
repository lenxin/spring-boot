package org.springframework.boot.buildpack.platform.docker.type;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

import org.apache.commons.compress.archivers.tar.TarArchiveEntry;
import org.apache.commons.compress.archivers.tar.TarArchiveInputStream;
import org.junit.jupiter.api.Test;
import org.skyscreamer.jsonassert.JSONAssert;

import org.springframework.boot.buildpack.platform.io.Owner;
import org.springframework.boot.buildpack.platform.json.AbstractJsonTests;
import org.springframework.util.StreamUtils;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Tests for {@link ImageArchive}.
 *


 */
class ImageArchiveTests extends AbstractJsonTests {

	@Test
	void fromImageWritesToValidArchiveTar() throws Exception {
		Image image = Image.of(getContent("image.json"));
		ImageArchive archive = ImageArchive.from(image, (update) -> {
			update.withNewLayer(Layer.of((layout) -> layout.directory("/spring", Owner.ROOT)));
			update.withTag(ImageReference.of("pack.local/builder/6b7874626575656b6162"));
		});
		ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
		archive.writeTo(outputStream);
		try (TarArchiveInputStream tar = new TarArchiveInputStream(
				new ByteArrayInputStream(outputStream.toByteArray()))) {
			TarArchiveEntry layerEntry = tar.getNextTarEntry();
			byte[] layerContent = read(tar, layerEntry.getSize());
			TarArchiveEntry configEntry = tar.getNextTarEntry();
			byte[] configContent = read(tar, configEntry.getSize());
			TarArchiveEntry manifestEntry = tar.getNextTarEntry();
			byte[] manifestContent = read(tar, manifestEntry.getSize());
			assertThat(tar.getNextTarEntry()).isNull();
			assertExpectedLayer(layerEntry, layerContent);
			assertExpectedConfig(configEntry, configContent);
			assertExpectedManifest(manifestEntry, manifestContent);
		}
	}

	private void assertExpectedLayer(TarArchiveEntry entry, byte[] content) throws Exception {
		assertThat(entry.getName()).isEqualTo("/bb09e17fd1bd2ee47155f1349645fcd9fff31e1247c7ed99cad469f1c16a4216.tar");
		try (TarArchiveInputStream tar = new TarArchiveInputStream(new ByteArrayInputStream(content))) {
			TarArchiveEntry contentEntry = tar.getNextTarEntry();
			assertThat(contentEntry.getName()).isEqualTo("/spring/");
		}
	}

	private void assertExpectedConfig(TarArchiveEntry entry, byte[] content) throws Exception {
		assertThat(entry.getName()).isEqualTo("/682f8d24b9d9c313d1190a0e955dcb5e65ec9beea40420999839c6f0cbb38382.json");
		String actualJson = new String(content, StandardCharsets.UTF_8);
		String expectedJson = StreamUtils.copyToString(getContent("image-archive-config.json"), StandardCharsets.UTF_8);
		JSONAssert.assertEquals(expectedJson, actualJson, false);
	}

	private void assertExpectedManifest(TarArchiveEntry entry, byte[] content) throws Exception {
		assertThat(entry.getName()).isEqualTo("/manifest.json");
		String actualJson = new String(content, StandardCharsets.UTF_8);
		String expectedJson = StreamUtils.copyToString(getContent("image-archive-manifest.json"),
				StandardCharsets.UTF_8);
		JSONAssert.assertEquals(expectedJson, actualJson, false);
	}

	private byte[] read(TarArchiveInputStream tar, long size) throws IOException {
		byte[] content = new byte[(int) size];
		tar.read(content);
		return content;
	}

}
