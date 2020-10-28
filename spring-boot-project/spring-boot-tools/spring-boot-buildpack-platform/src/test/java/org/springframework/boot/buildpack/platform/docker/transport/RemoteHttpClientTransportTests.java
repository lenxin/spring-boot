package org.springframework.boot.buildpack.platform.docker.transport;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.function.Consumer;

import javax.net.ssl.SSLContext;

import org.apache.http.HttpHost;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import org.springframework.boot.buildpack.platform.docker.configuration.DockerConfiguration;
import org.springframework.boot.buildpack.platform.docker.configuration.DockerHost;
import org.springframework.boot.buildpack.platform.docker.ssl.SslContextFactory;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

/**
 * Tests for {@link RemoteHttpClientTransport}
 *


 */
class RemoteHttpClientTransportTests {

	private final Map<String, String> environment = new LinkedHashMap<>();

	private final DockerConfiguration dockerConfiguration = new DockerConfiguration();

	@Test
	void createIfPossibleWhenDockerHostIsNotSetReturnsNull() {
		RemoteHttpClientTransport transport = RemoteHttpClientTransport.createIfPossible(this.environment::get,
				new DockerHost(null, false, null));
		assertThat(transport).isNull();
	}

	@Test
	void createIfPossibleWithoutDockerConfigurationReturnsNull() {
		RemoteHttpClientTransport transport = RemoteHttpClientTransport.createIfPossible(this.environment::get, null);
		assertThat(transport).isNull();
	}

	@Test
	void createIfPossibleWhenDockerHostInEnvironmentIsFileReturnsNull(@TempDir Path tempDir) throws IOException {
		String dummySocketFilePath = Files.createTempFile(tempDir, "remote-transport", null).toAbsolutePath()
				.toString();
		this.environment.put("DOCKER_HOST", dummySocketFilePath);
		RemoteHttpClientTransport transport = RemoteHttpClientTransport.createIfPossible(this.environment::get, null);
		assertThat(transport).isNull();
	}

	@Test
	void createIfPossibleWhenDockerHostInConfigurationIsFileReturnsNull(@TempDir Path tempDir) throws IOException {
		String dummySocketFilePath = Files.createTempFile(tempDir, "remote-transport", null).toAbsolutePath()
				.toString();
		RemoteHttpClientTransport transport = RemoteHttpClientTransport.createIfPossible(this.environment::get,
				new DockerHost(dummySocketFilePath, false, null));
		assertThat(transport).isNull();
	}

	@Test
	void createIfPossibleWhenDockerHostInEnvironmentIsAddressReturnsTransport() {
		this.environment.put("DOCKER_HOST", "tcp://192.168.1.2:2376");
		RemoteHttpClientTransport transport = RemoteHttpClientTransport.createIfPossible(this.environment::get, null);
		assertThat(transport).isNotNull();
	}

	@Test
	void createIfPossibleWhenDockerHostInConfigurationIsAddressReturnsTransport() {
		RemoteHttpClientTransport transport = RemoteHttpClientTransport.createIfPossible(this.environment::get,
				new DockerHost("tcp://192.168.1.2:2376", false, null));
		assertThat(transport).isNotNull();
	}

	@Test
	void createIfPossibleWhenTlsVerifyInEnvironmentWithMissingCertPathThrowsException() {
		this.environment.put("DOCKER_HOST", "tcp://192.168.1.2:2376");
		this.environment.put("DOCKER_TLS_VERIFY", "1");
		assertThatIllegalArgumentException()
				.isThrownBy(() -> RemoteHttpClientTransport.createIfPossible(this.environment::get, null))
				.withMessageContaining("Docker host TLS verification requires trust material");
	}

	@Test
	void createIfPossibleWhenTlsVerifyInConfigurationWithMissingCertPathThrowsException() {
		assertThatIllegalArgumentException()
				.isThrownBy(() -> RemoteHttpClientTransport.createIfPossible(this.environment::get,
						new DockerHost("tcp://192.168.1.2:2376", true, null)))
				.withMessageContaining("Docker host TLS verification requires trust material");
	}

	@Test
	void createIfPossibleWhenNoTlsVerifyUsesHttp() {
		this.environment.put("DOCKER_HOST", "tcp://192.168.1.2:2376");
		RemoteHttpClientTransport transport = RemoteHttpClientTransport.createIfPossible(this.environment::get, null);
		assertThat(transport.getHost()).satisfies(hostOf("http", "192.168.1.2", 2376));
	}

	@Test
	void createIfPossibleWhenTlsVerifyInEnvironmentUsesHttps() throws Exception {
		this.environment.put("DOCKER_HOST", "tcp://192.168.1.2:2376");
		this.environment.put("DOCKER_TLS_VERIFY", "1");
		this.environment.put("DOCKER_CERT_PATH", "/test-cert-path");
		SslContextFactory sslContextFactory = mock(SslContextFactory.class);
		given(sslContextFactory.forDirectory("/test-cert-path")).willReturn(SSLContext.getDefault());
		RemoteHttpClientTransport transport = RemoteHttpClientTransport.createIfPossible(this.environment::get,
				this.dockerConfiguration.getHost(), sslContextFactory);
		assertThat(transport.getHost()).satisfies(hostOf("https", "192.168.1.2", 2376));
	}

	@Test
	void createIfPossibleWhenTlsVerifyInConfigurationUsesHttps() throws Exception {
		SslContextFactory sslContextFactory = mock(SslContextFactory.class);
		given(sslContextFactory.forDirectory("/test-cert-path")).willReturn(SSLContext.getDefault());
		RemoteHttpClientTransport transport = RemoteHttpClientTransport.createIfPossible(this.environment::get,
				this.dockerConfiguration.withHost("tcp://192.168.1.2:2376", true, "/test-cert-path").getHost(),
				sslContextFactory);
		assertThat(transport.getHost()).satisfies(hostOf("https", "192.168.1.2", 2376));
	}

	private Consumer<HttpHost> hostOf(String scheme, String hostName, int port) {
		return (host) -> {
			assertThat(host).isNotNull();
			assertThat(host.getSchemeName()).isEqualTo(scheme);
			assertThat(host.getHostName()).isEqualTo(hostName);
			assertThat(host.getPort()).isEqualTo(port);
		};
	}

}
