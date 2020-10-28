package org.springframework.boot.buildpack.platform.docker.transport;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Collections;
import java.util.Map;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Tests for {@link HttpTransport}.
 *


 */
class HttpTransportTests {

	@Test
	void createWhenDockerHostVariableIsAddressReturnsRemote() {
		Map<String, String> environment = Collections.singletonMap("DOCKER_HOST", "tcp://192.168.1.0");
		HttpTransport transport = HttpTransport.create(environment::get);
		assertThat(transport).isInstanceOf(RemoteHttpClientTransport.class);
	}

	@Test
	void createWhenDockerHostVariableIsFileReturnsLocal(@TempDir Path tempDir) throws IOException {
		String dummySocketFilePath = Files.createTempFile(tempDir, "http-transport", null).toAbsolutePath().toString();
		Map<String, String> environment = Collections.singletonMap("DOCKER_HOST", dummySocketFilePath);
		HttpTransport transport = HttpTransport.create(environment::get);
		assertThat(transport).isInstanceOf(LocalHttpClientTransport.class);
	}

	@Test
	void createWhenDockerHostVariableIsUnixSchemePrefixedFileReturnsLocal(@TempDir Path tempDir) throws IOException {
		String dummySocketFilePath = "unix://"
				+ Files.createTempFile(tempDir, "http-transport", null).toAbsolutePath().toString();
		Map<String, String> environment = Collections.singletonMap("DOCKER_HOST", dummySocketFilePath);
		HttpTransport transport = HttpTransport.create(environment::get);
		assertThat(transport).isInstanceOf(LocalHttpClientTransport.class);
	}

	@Test
	void createWhenDoesNotHaveDockerHostVariableReturnsLocal() {
		HttpTransport transport = HttpTransport.create((name) -> null);
		assertThat(transport).isInstanceOf(LocalHttpClientTransport.class);
	}

}
