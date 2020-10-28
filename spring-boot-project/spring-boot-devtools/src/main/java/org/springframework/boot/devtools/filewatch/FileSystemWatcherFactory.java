package org.springframework.boot.devtools.filewatch;

/**
 * Factory used to create new {@link FileSystemWatcher} instances.
 *

 * @since 1.3.0
 */
@FunctionalInterface
public interface FileSystemWatcherFactory {

	/**
	 * Create a new {@link FileSystemWatcher}.
	 * @return a new {@link FileSystemWatcher}
	 */
	FileSystemWatcher getFileSystemWatcher();

}
