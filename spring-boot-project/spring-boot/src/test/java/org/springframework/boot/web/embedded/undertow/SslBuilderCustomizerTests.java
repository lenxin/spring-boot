package org.springframework.boot.web.embedded.undertow;

import java.net.InetAddress;
import java.security.NoSuchProviderException;

import javax.net.ssl.KeyManager;

import org.junit.jupiter.api.Test;

import org.springframework.boot.web.server.Ssl;
import org.springframework.boot.web.server.WebServerException;
import org.springframework.test.util.ReflectionTestUtils;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatIllegalStateException;

/**
 * Tests for {@link SslBuilderCustomizer}
 *


 */
class SslBuilderCustomizerTests {

	@Test
	void getKeyManagersWhenAliasIsNullShouldNotDecorate() throws Exception {
		Ssl ssl = new Ssl();
		ssl.setKeyPassword("password");
		ssl.setKeyStore("src/test/resources/test.jks");
		SslBuilderCustomizer customizer = new SslBuilderCustomizer(8080, InetAddress.getLocalHost(), ssl, null);
		KeyManager[] keyManagers = ReflectionTestUtils.invokeMethod(customizer, "getKeyManagers", ssl, null);
		Class<?> name = Class.forName(
				"org.springframework.boot.web.embedded.undertow.SslBuilderCustomizer$ConfigurableAliasKeyManager");
		assertThat(keyManagers[0]).isNotInstanceOf(name);
	}

	@Test
	void keyStoreProviderIsUsedWhenCreatingKeyStore() throws Exception {
		Ssl ssl = new Ssl();
		ssl.setKeyPassword("password");
		ssl.setKeyStore("src/test/resources/test.jks");
		ssl.setKeyStoreProvider("com.example.KeyStoreProvider");
		SslBuilderCustomizer customizer = new SslBuilderCustomizer(8080, InetAddress.getLocalHost(), ssl, null);
		assertThatIllegalStateException()
				.isThrownBy(() -> ReflectionTestUtils.invokeMethod(customizer, "getKeyManagers", ssl, null))
				.withCauseInstanceOf(NoSuchProviderException.class)
				.withMessageContaining("com.example.KeyStoreProvider");
	}

	@Test
	void trustStoreProviderIsUsedWhenCreatingTrustStore() throws Exception {
		Ssl ssl = new Ssl();
		ssl.setTrustStorePassword("password");
		ssl.setTrustStore("src/test/resources/test.jks");
		ssl.setTrustStoreProvider("com.example.TrustStoreProvider");
		SslBuilderCustomizer customizer = new SslBuilderCustomizer(8080, InetAddress.getLocalHost(), ssl, null);
		assertThatIllegalStateException()
				.isThrownBy(() -> ReflectionTestUtils.invokeMethod(customizer, "getTrustManagers", ssl, null))
				.withCauseInstanceOf(NoSuchProviderException.class)
				.withMessageContaining("com.example.TrustStoreProvider");
	}

	@Test
	void getKeyManagersWhenSslIsEnabledWithNoKeyStoreThrowsWebServerException() throws Exception {
		Ssl ssl = new Ssl();
		SslBuilderCustomizer customizer = new SslBuilderCustomizer(8080, InetAddress.getLocalHost(), ssl, null);
		assertThatIllegalStateException()
				.isThrownBy(() -> ReflectionTestUtils.invokeMethod(customizer, "getKeyManagers", ssl, null))
				.withCauseInstanceOf(WebServerException.class).withMessageContaining("Could not load key store 'null'");
	}

}
