package org.springframework.boot.buildpack.platform.docker.transport;

import java.io.IOException;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;

/**
 * Tests for {@link DockerEngineException}.
 *

 */
class DockerConnectionExceptionTests {

	private static final String HOST = "docker://localhost/";

	@Test
	void createWhenHostIsNullThrowsException() {
		assertThatIllegalArgumentException().isThrownBy(() -> new DockerConnectionException(null, null))
				.withMessage("Host must not be null");
	}

	@Test
	void createWhenCauseIsNullThrowsException() {
		assertThatIllegalArgumentException().isThrownBy(() -> new DockerConnectionException(HOST, null))
				.withMessage("Cause must not be null");
	}

	@Test
	void createWithIOException() {
		DockerConnectionException exception = new DockerConnectionException(HOST, new IOException("error"));
		assertThat(exception.getMessage())
				.contains("Connection to the Docker daemon at 'docker://localhost/' failed with error \"error\"");
	}

	@Test
	void createWithLastErrorException() {
		DockerConnectionException exception = new DockerConnectionException(HOST,
				new IOException(new com.sun.jna.LastErrorException("root cause")));
		assertThat(exception.getMessage())
				.contains("Connection to the Docker daemon at 'docker://localhost/' failed with error \"root cause\"");
	}

}
