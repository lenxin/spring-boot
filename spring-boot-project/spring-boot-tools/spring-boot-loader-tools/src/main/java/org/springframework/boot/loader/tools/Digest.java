package org.springframework.boot.loader.tools;

import java.io.IOException;
import java.security.DigestInputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Utility class used to calculate digests.
 *

 */
final class Digest {

	private Digest() {
	}

	/**
	 * Return the SHA1 digest from the supplied stream.
	 * @param supplier the stream supplier
	 * @return the SHA1 digest
	 * @throws IOException on IO error
	 */
	static String sha1(InputStreamSupplier supplier) throws IOException {
		try {
			try (DigestInputStream inputStream = new DigestInputStream(supplier.openStream(),
					MessageDigest.getInstance("SHA-1"))) {
				byte[] buffer = new byte[4098];
				while (inputStream.read(buffer) != -1) {
					// Read the entire stream
				}
				return bytesToHex(inputStream.getMessageDigest().digest());
			}
		}
		catch (NoSuchAlgorithmException ex) {
			throw new IllegalStateException(ex);
		}
	}

	private static String bytesToHex(byte[] bytes) {
		StringBuilder hex = new StringBuilder();
		for (byte b : bytes) {
			hex.append(String.format("%02x", b));
		}
		return hex.toString();
	}

}
