package org.springframework.boot.loader.tools;

import java.io.File;
import java.util.Collections;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

/**
 * Common {@link Layout}s.
 *




 * @since 1.0.0
 */
public final class Layouts {

	private Layouts() {
	}

	/**
	 * Return a layout for the given source file.
	 * @param file the source file
	 * @return a {@link Layout}
	 */
	public static Layout forFile(File file) {
		if (file == null) {
			throw new IllegalArgumentException("File must not be null");
		}
		String lowerCaseFileName = file.getName().toLowerCase(Locale.ENGLISH);
		if (lowerCaseFileName.endsWith(".jar")) {
			return new Jar();
		}
		if (lowerCaseFileName.endsWith(".war")) {
			return new War();
		}
		if (file.isDirectory() || lowerCaseFileName.endsWith(".zip")) {
			return new Expanded();
		}
		throw new IllegalStateException("Unable to deduce layout for '" + file + "'");
	}

	/**
	 * Executable JAR layout.
	 */
	public static class Jar implements RepackagingLayout {

		@Override
		public String getLauncherClassName() {
			return "org.springframework.boot.loader.JarLauncher";
		}

		@Override
		public String getLibraryLocation(String libraryName, LibraryScope scope) {
			return "BOOT-INF/lib/";
		}

		@Deprecated
		@Override
		public String getLibraryDestination(String libraryName, LibraryScope scope) {
			return "BOOT-INF/lib/";
		}

		@Override
		public String getClassesLocation() {
			return "";
		}

		@Override
		public String getRepackagedClassesLocation() {
			return "BOOT-INF/classes/";
		}

		@Override
		public String getClasspathIndexFileLocation() {
			return "BOOT-INF/classpath.idx";
		}

		@Override
		public String getLayersIndexFileLocation() {
			return "BOOT-INF/layers.idx";
		}

		@Override
		public boolean isExecutable() {
			return true;
		}

	}

	/**
	 * Executable expanded archive layout.
	 */
	public static class Expanded extends Jar {

		@Override
		public String getLauncherClassName() {
			return "org.springframework.boot.loader.PropertiesLauncher";
		}

	}

	/**
	 * No layout.
	 */
	public static class None extends Jar {

		@Override
		public String getLauncherClassName() {
			return null;
		}

		@Override
		public boolean isExecutable() {
			return false;
		}

	}

	/**
	 * Executable WAR layout.
	 */
	public static class War implements Layout {

		private static final Map<LibraryScope, String> SCOPE_LOCATION;

		static {
			Map<LibraryScope, String> locations = new HashMap<>();
			locations.put(LibraryScope.COMPILE, "WEB-INF/lib/");
			locations.put(LibraryScope.CUSTOM, "WEB-INF/lib/");
			locations.put(LibraryScope.RUNTIME, "WEB-INF/lib/");
			locations.put(LibraryScope.PROVIDED, "WEB-INF/lib-provided/");
			SCOPE_LOCATION = Collections.unmodifiableMap(locations);
		}

		@Override
		public String getLauncherClassName() {
			return "org.springframework.boot.loader.WarLauncher";
		}

		@Override
		public String getLibraryLocation(String libraryName, LibraryScope scope) {
			return SCOPE_LOCATION.get(scope);
		}

		@Deprecated
		@Override
		public String getLibraryDestination(String libraryName, LibraryScope scope) {
			return SCOPE_LOCATION.get(scope);
		}

		@Override
		public String getClassesLocation() {
			return "WEB-INF/classes/";
		}

		@Override
		public boolean isExecutable() {
			return true;
		}

	}

}
