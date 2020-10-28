package org.springframework.boot.loader.tools;

/**
 * Strategy interface used to determine the layout for a particular type of archive.
 * Layouts may additionally implement {@link CustomLoaderLayout} if they wish to write
 * custom loader classes.
 *

 * @since 1.0.0
 * @see Layouts
 * @see RepackagingLayout
 * @see CustomLoaderLayout
 */
public interface Layout {

	/**
	 * Returns the launcher class name for this layout.
	 * @return the launcher class name
	 */
	String getLauncherClassName();

	/**
	 * Returns the destination path for a given library.
	 * @param libraryName the name of the library (excluding any path)
	 * @param scope the scope of the library
	 * @return the location of the library relative to the root of the archive (should end
	 * with '/') or {@code null} if the library should not be included.
	 */
	default String getLibraryLocation(String libraryName, LibraryScope scope) {
		return getLibraryDestination(libraryName, scope);
	}

	/**
	 * Returns the destination path for a given library.
	 * @param libraryName the name of the library (excluding any path)
	 * @param scope the scope of the library
	 * @return the destination relative to the root of the archive (should end with '/')
	 * or {@code null} if the library should not be included.
	 * @deprecated since 2.3.0 in favor of {@link #getLibraryLocation}
	 */
	@Deprecated
	String getLibraryDestination(String libraryName, LibraryScope scope);

	/**
	 * Returns the location of classes within the archive.
	 * @return the classes location
	 */
	String getClassesLocation();

	/**
	 * Returns if loader classes should be included to make the archive executable.
	 * @return if the layout is executable
	 */
	boolean isExecutable();

}
