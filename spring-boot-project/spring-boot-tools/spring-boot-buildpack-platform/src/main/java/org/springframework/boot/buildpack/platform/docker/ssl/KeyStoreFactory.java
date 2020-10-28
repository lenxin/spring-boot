package org.springframework.boot.buildpack.platform.docker.ssl;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.security.GeneralSecurityException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

/**
 * Utility methods for creating Java trust material from key and certificate files.
 *

 */
final class KeyStoreFactory {

	private static final char[] NO_PASSWORD = {};

	private KeyStoreFactory() {
	}

	/**
	 * Create a new {@link KeyStore} populated with the certificate stored at the
	 * specified file path and an optional private key.
	 * @param certPath the path to the certificate authority file
	 * @param keyPath the path to the private file
	 * @param alias the alias to use for KeyStore entries
	 * @return the {@code KeyStore}
	 */
	static KeyStore create(Path certPath, Path keyPath, String alias) {
		try {
			KeyStore keyStore = getKeyStore();
			X509Certificate[] certificates = CertificateParser.parse(certPath);
			PrivateKey privateKey = getPrivateKey(keyPath);
			try {
				addCertificates(keyStore, certificates, privateKey, alias);
			}
			catch (KeyStoreException ex) {
				throw new IllegalStateException("Error adding certificates to KeyStore: " + ex.getMessage(), ex);
			}
			return keyStore;
		}
		catch (GeneralSecurityException | IOException ex) {
			throw new IllegalStateException("Error creating KeyStore: " + ex.getMessage(), ex);
		}
	}

	private static KeyStore getKeyStore()
			throws KeyStoreException, IOException, NoSuchAlgorithmException, CertificateException {
		KeyStore keyStore = KeyStore.getInstance(KeyStore.getDefaultType());
		keyStore.load(null);
		return keyStore;
	}

	private static PrivateKey getPrivateKey(Path path) {
		if (path != null && Files.exists(path)) {
			return PrivateKeyParser.parse(path);
		}
		return null;
	}

	private static void addCertificates(KeyStore keyStore, X509Certificate[] certificates, PrivateKey privateKey,
			String alias) throws KeyStoreException {
		if (privateKey != null) {
			keyStore.setKeyEntry(alias, privateKey, NO_PASSWORD, certificates);
		}
		else {
			for (int index = 0; index < certificates.length; index++) {
				keyStore.setCertificateEntry(alias + "-" + index, certificates[index]);
			}
		}
	}

}
