package org.springframework.boot.jarmode.layertools;

import java.io.InputStreamReader;
import java.util.zip.ZipEntry;

import org.junit.jupiter.api.Test;

import org.springframework.core.io.ClassPathResource;
import org.springframework.util.FileCopyUtils;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatIllegalStateException;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

/**
 * Tests for {@link IndexedLayers}.
 *


 */
class IndexedLayersTests {

	@Test
	void createWhenIndexFileIsEmptyThrowsException() {
		assertThatIllegalStateException().isThrownBy(() -> new IndexedLayers(" \n "))
				.withMessage("Empty layer index file loaded");
	}

	@Test
	void createWhenIndexFileIsMalformedThrowsException() throws Exception {
		assertThatIllegalStateException().isThrownBy(() -> new IndexedLayers("test"))
				.withMessage("Layer index file is malformed");
	}

	@Test
	void iteratorReturnsLayers() throws Exception {
		IndexedLayers layers = new IndexedLayers(getIndex());
		assertThat(layers).containsExactly("test", "empty", "application");
	}

	@Test
	void getLayerWhenMatchesNameReturnsLayer() throws Exception {
		IndexedLayers layers = new IndexedLayers(getIndex());
		assertThat(layers.getLayer(mockEntry("BOOT-INF/lib/a.jar"))).isEqualTo("test");
		assertThat(layers.getLayer(mockEntry("BOOT-INF/classes/Demo.class"))).isEqualTo("application");
	}

	@Test
	void getLayerWhenMatchesNameForMissingLayerThrowsException() throws Exception {
		IndexedLayers layers = new IndexedLayers(getIndex());
		assertThatIllegalStateException().isThrownBy(() -> layers.getLayer(mockEntry("file.jar")))
				.withMessage("No layer defined in index for file " + "'file.jar'");
	}

	@Test
	void getLayerWhenMatchesDirectoryReturnsLayer() throws Exception {
		IndexedLayers layers = new IndexedLayers(getIndex());
		assertThat(layers.getLayer(mockEntry("META-INF/MANIFEST.MF"))).isEqualTo("application");
		assertThat(layers.getLayer(mockEntry("META-INF/a/sub/directory/and/a/file"))).isEqualTo("application");
	}

	@Test
	void getLayerWhenFileHasSpaceReturnsLayer() throws Exception {
		IndexedLayers layers = new IndexedLayers(getIndex());
		assertThat(layers.getLayer(mockEntry("a b/c d"))).isEqualTo("application");
	}

	private String getIndex() throws Exception {
		ClassPathResource resource = new ClassPathResource("test-layers.idx", getClass());
		InputStreamReader reader = new InputStreamReader(resource.getInputStream());
		return FileCopyUtils.copyToString(reader);
	}

	private ZipEntry mockEntry(String name) {
		ZipEntry entry = mock(ZipEntry.class);
		given(entry.getName()).willReturn(name);
		return entry;
	}

}
