package org.springframework.boot.web.server;

import java.security.KeyStore;
import java.security.KeyStoreException;

import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

/**
 * Provides utilities around SSL.
 *

 * @since 2.1.13
 */
public final class SslConfigurationValidator {

	private SslConfigurationValidator() {
	}

	public static void validateKeyAlias(KeyStore keyStore, String keyAlias) {
		if (StringUtils.hasLength(keyAlias)) {
			try {
				Assert.state(keyStore.containsAlias(keyAlias),
						() -> String.format("Keystore does not contain specified alias '%s'", keyAlias));
			}
			catch (KeyStoreException ex) {
				throw new IllegalStateException(
						String.format("Could not determine if keystore contains alias '%s'", keyAlias), ex);
			}
		}
	}

}
