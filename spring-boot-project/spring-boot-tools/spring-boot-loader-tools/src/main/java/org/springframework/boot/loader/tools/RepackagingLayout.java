package org.springframework.boot.loader.tools;

/**
 * A specialization of {@link Layout} that repackages an existing archive by moving its
 * content to a new location.
 *

 * @since 1.4.0
 */
public interface RepackagingLayout extends Layout {

	/**
	 * Returns the location to which classes should be moved.
	 * @return the repackaged classes location
	 */
	String getRepackagedClassesLocation();

	/**
	 * Returns the location of the classpath index file that should be written or
	 * {@code null} if not index is required. The result should include the filename and
	 * is relative to the root of the jar.
	 * @return the classpath index file location
	 * @since 2.3.0
	 */
	default String getClasspathIndexFileLocation() {
		return null;
	}

	/**
	 * Returns the location of the layer index file that should be written or {@code null}
	 * if not index is required. The result should include the filename and is relative to
	 * the root of the jar.
	 * @return the layer index file location
	 * @since 2.3.0
	 */
	default String getLayersIndexFileLocation() {
		return null;
	}

}
