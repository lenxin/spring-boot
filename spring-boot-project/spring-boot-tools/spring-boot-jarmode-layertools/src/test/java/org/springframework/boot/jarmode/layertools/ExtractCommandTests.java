package org.springframework.boot.jarmode.layertools;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.io.TempDir;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatIllegalStateException;
import static org.mockito.BDDMockito.given;

/**
 * Tests for {@link ExtractCommand}.
 *

 */
@ExtendWith(MockitoExtension.class)
class ExtractCommandTests {

	@TempDir
	File temp;

	@Mock
	private Context context;

	private File jarFile;

	private File extract;

	private Layers layers = new TestLayers();

	private ExtractCommand command;

	@BeforeEach
	void setup() throws Exception {
		this.jarFile = createJarFile("test.jar");
		this.extract = new File(this.temp, "extract");
		this.extract.mkdir();
		this.command = new ExtractCommand(this.context, this.layers);
	}

	@Test
	void runExtractsLayers() throws Exception {
		given(this.context.getJarFile()).willReturn(this.jarFile);
		given(this.context.getWorkingDir()).willReturn(this.extract);
		this.command.run(Collections.emptyMap(), Collections.emptyList());
		assertThat(this.extract.list()).containsOnly("a", "b", "c", "d");
		assertThat(new File(this.extract, "a/a/a.jar")).exists();
		assertThat(new File(this.extract, "b/b/b.jar")).exists();
		assertThat(new File(this.extract, "c/c/c.jar")).exists();
		assertThat(new File(this.extract, "d")).isDirectory();
	}

	@Test
	void runWhenHasDestinationOptionExtractsLayers() {
		given(this.context.getJarFile()).willReturn(this.jarFile);
		File out = new File(this.extract, "out");
		this.command.run(Collections.singletonMap(ExtractCommand.DESTINATION_OPTION, out.getAbsolutePath()),
				Collections.emptyList());
		assertThat(this.extract.list()).containsOnly("out");
		assertThat(new File(this.extract, "out/a/a/a.jar")).exists();
		assertThat(new File(this.extract, "out/b/b/b.jar")).exists();
		assertThat(new File(this.extract, "out/c/c/c.jar")).exists();
	}

	@Test
	void runWhenHasLayerParamsExtractsLimitedLayers() {
		given(this.context.getJarFile()).willReturn(this.jarFile);
		given(this.context.getWorkingDir()).willReturn(this.extract);
		this.command.run(Collections.emptyMap(), Arrays.asList("a", "c"));
		assertThat(this.extract.list()).containsOnly("a", "c");
		assertThat(new File(this.extract, "a/a/a.jar")).exists();
		assertThat(new File(this.extract, "c/c/c.jar")).exists();
	}

	@Test
	void runWithJarFileContainingNoEntriesFails() throws IOException {
		File file = new File(this.temp, "empty.jar");
		try (FileWriter writer = new FileWriter(file)) {
			writer.write("text");
		}
		given(this.context.getJarFile()).willReturn(file);
		given(this.context.getWorkingDir()).willReturn(this.extract);
		assertThatIllegalStateException()
				.isThrownBy(() -> this.command.run(Collections.emptyMap(), Collections.emptyList()))
				.withMessageContaining("not compatible with layertools");
	}

	private File createJarFile(String name) throws IOException {
		File file = new File(this.temp, name);
		try (ZipOutputStream out = new ZipOutputStream(new FileOutputStream(file))) {
			out.putNextEntry(new ZipEntry("a/"));
			out.closeEntry();
			out.putNextEntry(new ZipEntry("a/a.jar"));
			out.closeEntry();
			out.putNextEntry(new ZipEntry("b/"));
			out.closeEntry();
			out.putNextEntry(new ZipEntry("b/b.jar"));
			out.closeEntry();
			out.putNextEntry(new ZipEntry("c/"));
			out.closeEntry();
			out.putNextEntry(new ZipEntry("c/c.jar"));
			out.closeEntry();
			out.putNextEntry(new ZipEntry("d/"));
			out.closeEntry();
		}
		return file;
	}

	private static class TestLayers implements Layers {

		@Override
		public Iterator<String> iterator() {
			return Arrays.asList("a", "b", "c", "d").iterator();
		}

		@Override
		public String getLayer(ZipEntry entry) {
			if (entry.getName().startsWith("a")) {
				return "a";
			}
			if (entry.getName().startsWith("b")) {
				return "b";
			}
			return "c";
		}

	}

}
