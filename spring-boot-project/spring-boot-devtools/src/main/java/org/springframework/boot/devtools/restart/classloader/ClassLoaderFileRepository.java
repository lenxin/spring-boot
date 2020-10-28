package org.springframework.boot.devtools.restart.classloader;

/**
 * A container for files that may be served from a {@link ClassLoader}. Can be used to
 * represent files that have been added, modified or deleted since the original JAR was
 * created.
 *

 * @since 1.3.0
 * @see ClassLoaderFile
 */
@FunctionalInterface
public interface ClassLoaderFileRepository {

	/**
	 * Empty {@link ClassLoaderFileRepository} implementation.
	 */
	ClassLoaderFileRepository NONE = (name) -> null;

	/**
	 * Return a {@link ClassLoaderFile} for the given name or {@code null} if no file is
	 * contained in this collection.
	 * @param name the name of the file
	 * @return a {@link ClassLoaderFile} or {@code null}
	 */
	ClassLoaderFile getFile(String name);

}
