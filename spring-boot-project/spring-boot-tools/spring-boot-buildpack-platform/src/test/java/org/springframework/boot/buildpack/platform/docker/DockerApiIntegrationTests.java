package org.springframework.boot.buildpack.platform.docker;

import java.io.IOException;

import org.junit.jupiter.api.Test;

import org.springframework.boot.buildpack.platform.docker.type.ImageReference;
import org.springframework.boot.testsupport.testcontainers.DisabledIfDockerUnavailable;

/**
 * Integration tests for {@link DockerApi}.
 *

 */
@DisabledIfDockerUnavailable
class DockerApiIntegrationTests {

	private final DockerApi docker = new DockerApi();

	@Test
	void pullImage() throws IOException {
		this.docker.image().pull(ImageReference.of("gcr.io/paketo-buildpacks/builder:base"),
				new TotalProgressPullListener(new TotalProgressBar("Pulling: ")));
	}

}
