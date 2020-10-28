package org.springframework.boot.web.embedded.jetty;

import java.net.InetSocketAddress;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.jetty.alpn.server.ALPNServerConnectionFactory;
import org.eclipse.jetty.http2.server.HTTP2ServerConnectionFactory;
import org.eclipse.jetty.server.ConnectionFactory;
import org.eclipse.jetty.server.HttpConnectionFactory;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.SslConnectionFactory;
import org.eclipse.jetty.util.ssl.SslContextFactory;
import org.junit.jupiter.api.Test;

import org.springframework.boot.web.server.Http2;
import org.springframework.boot.web.server.Ssl;
import org.springframework.boot.web.server.WebServerException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

/**
 * Tests for {@link SslServerCustomizer}.
 *

 */
class SslServerCustomizerTests {

	@Test
	@SuppressWarnings("rawtypes")
	void whenHttp2IsNotEnabledServerConnectorHasSslAndHttpConnectionFactories() {
		Server server = createCustomizedServer();
		assertThat(server.getConnectors()).hasSize(1);
		List<ConnectionFactory> factories = new ArrayList<>(server.getConnectors()[0].getConnectionFactories());
		assertThat(factories).extracting((factory) -> (Class) factory.getClass())
				.containsExactly(SslConnectionFactory.class, HttpConnectionFactory.class);
	}

	@Test
	@SuppressWarnings("rawtypes")
	void whenHttp2IsEnabledServerConnectorsHasSslAlpnH2AndHttpConnectionFactories() {
		Http2 http2 = new Http2();
		http2.setEnabled(true);
		Server server = createCustomizedServer(http2);
		assertThat(server.getConnectors()).hasSize(1);
		List<ConnectionFactory> factories = new ArrayList<>(server.getConnectors()[0].getConnectionFactories());
		assertThat(factories).extracting((factory) -> (Class) factory.getClass()).containsExactly(
				SslConnectionFactory.class, ALPNServerConnectionFactory.class, HTTP2ServerConnectionFactory.class,
				HttpConnectionFactory.class);
	}

	@Test
	void alpnConnectionFactoryHasNullDefaultProtocolToAllowNegotiationToHttp11() {
		Http2 http2 = new Http2();
		http2.setEnabled(true);
		Server server = createCustomizedServer(http2);
		assertThat(server.getConnectors()).hasSize(1);
		List<ConnectionFactory> factories = new ArrayList<>(server.getConnectors()[0].getConnectionFactories());
		assertThat(((ALPNServerConnectionFactory) factories.get(1)).getDefaultProtocol()).isNull();
	}

	@Test
	void configureSslWhenSslIsEnabledWithNoKeyStoreThrowsWebServerException() {
		Ssl ssl = new Ssl();
		SslServerCustomizer customizer = new SslServerCustomizer(null, ssl, null, null);
		assertThatExceptionOfType(Exception.class)
				.isThrownBy(() -> customizer.configureSsl(new SslContextFactory.Server(), ssl, null))
				.satisfies((ex) -> {
					assertThat(ex).isInstanceOf(WebServerException.class);
					assertThat(ex).hasMessageContaining("Could not load key store 'null'");
				});
	}

	private Server createCustomizedServer() {
		return createCustomizedServer(new Http2());
	}

	private Server createCustomizedServer(Http2 http2) {
		Ssl ssl = new Ssl();
		ssl.setKeyStore("classpath:test.jks");
		return createCustomizedServer(ssl, http2);
	}

	private Server createCustomizedServer(Ssl ssl, Http2 http2) {
		Server server = new Server();
		new SslServerCustomizer(new InetSocketAddress(0), ssl, null, http2).customize(server);
		return server;
	}

}
