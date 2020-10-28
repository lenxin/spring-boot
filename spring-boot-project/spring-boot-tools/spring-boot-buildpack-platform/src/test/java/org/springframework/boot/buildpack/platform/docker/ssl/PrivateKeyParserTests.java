package org.springframework.boot.buildpack.platform.docker.ssl;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.PrivateKey;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatIllegalStateException;

/**
 * Tests for {@link PrivateKeyParser}.
 *

 */
class PrivateKeyParserTests {

	private PemFileWriter fileWriter;

	@BeforeEach
	void setUp() throws IOException {
		this.fileWriter = new PemFileWriter();
	}

	@AfterEach
	void tearDown() throws IOException {
		this.fileWriter.cleanup();
	}

	@Test
	void parsePkcs8KeyFile() throws IOException {
		Path path = this.fileWriter.writeFile("key.pem", PemFileWriter.CA_PRIVATE_KEY);
		PrivateKey privateKey = PrivateKeyParser.parse(path);
		assertThat(privateKey).isNotNull();
		assertThat(privateKey.getFormat()).isEqualTo("PKCS#8");
		Files.delete(path);
	}

	@Test
	void parsePkcs1KeyFile() throws IOException {
		Path path = this.fileWriter.writeFile("key.pem", PemFileWriter.PRIVATE_KEY);
		PrivateKey privateKey = PrivateKeyParser.parse(path);
		assertThat(privateKey).isNotNull();
		// keys in PKCS#1 format are converted to PKCS#8 for parsing
		assertThat(privateKey.getFormat()).isEqualTo("PKCS#8");
		Files.delete(path);
	}

	@Test
	void parseWithNonKeyFileWillThrowException() throws IOException {
		Path path = this.fileWriter.writeFile("text.pem", "plain text");
		assertThatIllegalStateException().isThrownBy(() -> PrivateKeyParser.parse(path))
				.withMessageContaining(path.toString());
		Files.delete(path);
	}

	@Test
	void parseWithInvalidPathWillThrowException() throws URISyntaxException {
		Path path = Paths.get(new URI("file:///bad/path/key.pem"));
		assertThatIllegalStateException().isThrownBy(() -> PrivateKeyParser.parse(path))
				.withMessageContaining(path.toString());
	}

}
