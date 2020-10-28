package org.springframework.boot.maven;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.jar.JarEntry;
import java.util.jar.JarInputStream;
import java.util.jar.JarOutputStream;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Tests for {@link PropertiesMergingResourceTransformer}.
 *

 */
class PropertiesMergingResourceTransformerTests {

	private final PropertiesMergingResourceTransformer transformer = new PropertiesMergingResourceTransformer();

	@Test
	void testProcess() throws Exception {
		assertThat(this.transformer.hasTransformedResource()).isFalse();
		this.transformer.processResource("foo", new ByteArrayInputStream("foo=bar".getBytes()), null, 0);
		assertThat(this.transformer.hasTransformedResource()).isTrue();
	}

	@Test
	void testMerge() throws Exception {
		this.transformer.processResource("foo", new ByteArrayInputStream("foo=bar".getBytes()), null, 0);
		this.transformer.processResource("bar", new ByteArrayInputStream("foo=spam".getBytes()), null, 0);
		assertThat(this.transformer.getData().getProperty("foo")).isEqualTo("bar,spam");
	}

	@Test
	void testOutput() throws Exception {
		this.transformer.setResource("foo");
		long time = 1592911068000L;
		this.transformer.processResource("foo", new ByteArrayInputStream("foo=bar".getBytes()), null, time);
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		JarOutputStream os = new JarOutputStream(out);
		this.transformer.modifyOutputStream(os);
		os.flush();
		os.close();
		byte[] bytes = out.toByteArray();
		assertThat(bytes).hasSizeGreaterThan(0);
		List<JarEntry> entries = new ArrayList<>();
		try (JarInputStream is = new JarInputStream(new ByteArrayInputStream(bytes))) {
			JarEntry entry;
			while ((entry = is.getNextJarEntry()) != null) {
				entries.add(entry);
			}
		}
		assertThat(entries).hasSize(1);
		assertThat(entries.get(0).getTime()).isEqualTo(time);
	}

}
