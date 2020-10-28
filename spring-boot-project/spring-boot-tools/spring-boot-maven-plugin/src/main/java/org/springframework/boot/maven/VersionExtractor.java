package org.springframework.boot.maven;

import java.io.File;
import java.io.IOException;
import java.net.JarURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.jar.Attributes;
import java.util.jar.JarFile;

/**
 * Extracts version information for a Class.
 *


 */
final class VersionExtractor {

	private VersionExtractor() {
	}

	/**
	 * Return the version information for the provided {@link Class}.
	 * @param cls the Class to retrieve the version for
	 * @return the version, or {@code null} if a version can not be extracted
	 */
	static String forClass(Class<?> cls) {
		String implementationVersion = cls.getPackage().getImplementationVersion();
		if (implementationVersion != null) {
			return implementationVersion;
		}
		URL codeSourceLocation = cls.getProtectionDomain().getCodeSource().getLocation();
		try {
			URLConnection connection = codeSourceLocation.openConnection();
			if (connection instanceof JarURLConnection) {
				return getImplementationVersion(((JarURLConnection) connection).getJarFile());
			}
			try (JarFile jarFile = new JarFile(new File(codeSourceLocation.toURI()))) {
				return getImplementationVersion(jarFile);
			}
		}
		catch (Exception ex) {
			return null;
		}
	}

	private static String getImplementationVersion(JarFile jarFile) throws IOException {
		return jarFile.getManifest().getMainAttributes().getValue(Attributes.Name.IMPLEMENTATION_VERSION);
	}

}
