package org.springframework.boot.loader.tools;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

/**
 * {@link Library} implementation for internal jarmode jars.
 *

 * @since 2.3.0
 */
public class JarModeLibrary extends Library {

	/**
	 * {@link JarModeLibrary} for layer tools.
	 */
	public static final JarModeLibrary LAYER_TOOLS = new JarModeLibrary("spring-boot-jarmode-layertools");

	JarModeLibrary(String artifactId) {
		this(createCoordinates(artifactId));
	}

	public JarModeLibrary(LibraryCoordinates coordinates) {
		super(getJarName(coordinates), null, LibraryScope.RUNTIME, coordinates, false);
	}

	private static LibraryCoordinates createCoordinates(String artifactId) {
		String version = JarModeLibrary.class.getPackage().getImplementationVersion();
		return LibraryCoordinates.of("org.springframework.boot", artifactId, version);
	}

	private static String getJarName(LibraryCoordinates coordinates) {
		String version = coordinates.getVersion();
		StringBuilder jarName = new StringBuilder(coordinates.getArtifactId());
		if (StringUtils.hasText(version)) {
			jarName.append('-');
			jarName.append(version);
		}
		jarName.append(".jar");
		return jarName.toString();
	}

	@Override
	public InputStream openStream() throws IOException {
		String path = "META-INF/jarmode/" + getCoordinates().getArtifactId() + ".jar";
		URL resource = getClass().getClassLoader().getResource(path);
		Assert.state(resource != null, () -> "Unable to find resource " + path);
		return resource.openStream();
	}

	@Override
	long getLastModified() {
		return 0L;
	}

	@Override
	public File getFile() {
		throw new UnsupportedOperationException("Unable to access jar mode library file");
	}

}
