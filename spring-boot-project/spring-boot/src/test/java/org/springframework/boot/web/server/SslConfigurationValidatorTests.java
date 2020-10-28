package org.springframework.boot.web.server;

import java.io.File;
import java.io.FileInputStream;
import java.security.KeyStore;
import java.security.KeyStoreException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

/**
 * Tests for {@link SslConfigurationValidator}.
 *

 */

class SslConfigurationValidatorTests {

	private static final String VALID_ALIAS = "test-alias";

	private static final String INVALID_ALIAS = "test-alias-5150";

	private KeyStore keyStore;

	@BeforeEach
	void loadKeystore() throws Exception {
		this.keyStore = KeyStore.getInstance(KeyStore.getDefaultType());
		this.keyStore.load(new FileInputStream(new File("src/test/resources/test.jks")), "secret".toCharArray());
	}

	@Test
	void validateKeyAliasWhenAliasFoundShouldNotFail() {
		SslConfigurationValidator.validateKeyAlias(this.keyStore, VALID_ALIAS);
	}

	@Test
	void validateKeyAliasWhenNullAliasShouldNotFail() {
		SslConfigurationValidator.validateKeyAlias(this.keyStore, null);
	}

	@Test
	void validateKeyAliasWhenEmptyAliasShouldNotFail() {
		SslConfigurationValidator.validateKeyAlias(this.keyStore, "");
	}

	@Test
	void validateKeyAliasWhenAliasNotFoundShouldThrowException() {
		assertThatThrownBy(() -> SslConfigurationValidator.validateKeyAlias(this.keyStore, INVALID_ALIAS))
				.isInstanceOf(IllegalStateException.class)
				.hasMessage("Keystore does not contain specified alias '" + INVALID_ALIAS + "'");
	}

	@Test
	void validateKeyAliasWhenKeyStoreThrowsExceptionOnContains() throws KeyStoreException {
		KeyStore uninitializedKeyStore = KeyStore.getInstance(KeyStore.getDefaultType());
		assertThatThrownBy(() -> SslConfigurationValidator.validateKeyAlias(uninitializedKeyStore, "alias"))
				.isInstanceOf(IllegalStateException.class)
				.hasMessage("Could not determine if keystore contains alias 'alias'");
	}

}
