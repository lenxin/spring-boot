package org.springframework.boot.devtools.filewatch;

import java.io.File;
import java.util.Collections;
import java.util.Iterator;
import java.util.Set;

/**
 * A collections of files from a specific source directory that have changed.
 *

 * @since 1.3.0
 * @see FileChangeListener
 * @see ChangedFiles
 */
public final class ChangedFiles implements Iterable<ChangedFile> {

	private final File sourceDirectory;

	private final Set<ChangedFile> files;

	public ChangedFiles(File sourceDirectory, Set<ChangedFile> files) {
		this.sourceDirectory = sourceDirectory;
		this.files = Collections.unmodifiableSet(files);
	}

	/**
	 * The source directory being watched.
	 * @return the source directory
	 */
	public File getSourceDirectory() {
		return this.sourceDirectory;
	}

	@Override
	public Iterator<ChangedFile> iterator() {
		return getFiles().iterator();
	}

	/**
	 * The files that have been changed.
	 * @return the changed files
	 */
	public Set<ChangedFile> getFiles() {
		return this.files;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == null) {
			return false;
		}
		if (obj == this) {
			return true;
		}
		if (obj instanceof ChangedFiles) {
			ChangedFiles other = (ChangedFiles) obj;
			return this.sourceDirectory.equals(other.sourceDirectory) && this.files.equals(other.files);
		}
		return super.equals(obj);
	}

	@Override
	public int hashCode() {
		return this.files.hashCode();
	}

	@Override
	public String toString() {
		return this.sourceDirectory + " " + this.files;
	}

}
