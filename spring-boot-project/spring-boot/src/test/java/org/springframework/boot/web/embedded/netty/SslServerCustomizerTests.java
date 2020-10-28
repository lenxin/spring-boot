package org.springframework.boot.web.embedded.netty;

import java.security.NoSuchProviderException;

import org.junit.jupiter.api.Test;

import org.springframework.boot.web.server.Ssl;
import org.springframework.boot.web.server.WebServerException;

import static org.assertj.core.api.Assertions.assertThatIllegalStateException;

/**
 * Tests for {@link SslServerCustomizer}.
 *


 */
class SslServerCustomizerTests {

	@Test
	void keyStoreProviderIsUsedWhenCreatingKeyStore() throws Exception {
		Ssl ssl = new Ssl();
		ssl.setKeyPassword("password");
		ssl.setKeyStore("src/test/resources/test.jks");
		ssl.setKeyStoreProvider("com.example.KeyStoreProvider");
		SslServerCustomizer customizer = new SslServerCustomizer(ssl, null, null);
		assertThatIllegalStateException().isThrownBy(() -> customizer.getKeyManagerFactory(ssl, null))
				.withCauseInstanceOf(NoSuchProviderException.class)
				.withMessageContaining("com.example.KeyStoreProvider");
	}

	@Test
	void trustStoreProviderIsUsedWhenCreatingTrustStore() throws Exception {
		Ssl ssl = new Ssl();
		ssl.setTrustStorePassword("password");
		ssl.setTrustStore("src/test/resources/test.jks");
		ssl.setTrustStoreProvider("com.example.TrustStoreProvider");
		SslServerCustomizer customizer = new SslServerCustomizer(ssl, null, null);
		assertThatIllegalStateException().isThrownBy(() -> customizer.getTrustManagerFactory(ssl, null))
				.withCauseInstanceOf(NoSuchProviderException.class)
				.withMessageContaining("com.example.TrustStoreProvider");
	}

	@Test
	void getKeyManagerFactoryWhenSslIsEnabledWithNoKeyStoreThrowsWebServerException() throws Exception {
		Ssl ssl = new Ssl();
		SslServerCustomizer customizer = new SslServerCustomizer(ssl, null, null);
		assertThatIllegalStateException().isThrownBy(() -> customizer.getKeyManagerFactory(ssl, null))
				.withCauseInstanceOf(WebServerException.class).withMessageContaining("Could not load key store 'null'");
	}

}
