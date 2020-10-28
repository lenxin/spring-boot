package org.springframework.boot.buildpack.platform.docker.configuration;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Tests for {@link DockerConfiguration}.
 *


 */
public class DockerConfigurationTests {

	@Test
	void createDockerConfigurationWithDefaults() {
		DockerConfiguration configuration = new DockerConfiguration();
		assertThat(configuration.getBuilderRegistryAuthentication()).isNull();
	}

	@Test
	void createDockerConfigurationWithUserAuth() {
		DockerConfiguration configuration = new DockerConfiguration().withBuilderRegistryUserAuthentication("user",
				"secret", "https://docker.example.com", "docker@example.com");
		DockerRegistryAuthentication auth = configuration.getBuilderRegistryAuthentication();
		assertThat(auth).isNotNull();
		assertThat(auth).isInstanceOf(DockerRegistryUserAuthentication.class);
		DockerRegistryUserAuthentication userAuth = (DockerRegistryUserAuthentication) auth;
		assertThat(userAuth.getUrl()).isEqualTo("https://docker.example.com");
		assertThat(userAuth.getUsername()).isEqualTo("user");
		assertThat(userAuth.getPassword()).isEqualTo("secret");
		assertThat(userAuth.getEmail()).isEqualTo("docker@example.com");
	}

	@Test
	void createDockerConfigurationWithTokenAuth() {
		DockerConfiguration configuration = new DockerConfiguration().withBuilderRegistryTokenAuthentication("token");
		DockerRegistryAuthentication auth = configuration.getBuilderRegistryAuthentication();
		assertThat(auth).isNotNull();
		assertThat(auth).isInstanceOf(DockerRegistryTokenAuthentication.class);
		DockerRegistryTokenAuthentication tokenAuth = (DockerRegistryTokenAuthentication) auth;
		assertThat(tokenAuth.getToken()).isEqualTo("token");
	}

}
