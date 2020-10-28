package org.springframework.boot.loader.tools;

import java.io.File;
import java.io.IOException;
import java.nio.file.attribute.FileTime;
import java.util.jar.JarFile;

import org.springframework.boot.loader.tools.Layouts.War;
import org.springframework.util.Assert;

/**
 * Utility class that can be used to repackage an archive so that it can be executed using
 * '{@literal java -jar}'.
 *




 * @since 1.0.0
 */
public class Repackager extends Packager {

	private boolean backupSource = true;

	public Repackager(File source) {
		this(source, null);
	}

	public Repackager(File source, LayoutFactory layoutFactory) {
		super(source, layoutFactory);
	}

	/**
	 * Sets if source files should be backed up when they would be overwritten.
	 * @param backupSource if source files should be backed up
	 */
	public void setBackupSource(boolean backupSource) {
		this.backupSource = backupSource;
	}

	/**
	 * Repackage the source file so that it can be run using '{@literal java -jar}'.
	 * @param libraries the libraries required to run the archive
	 * @throws IOException if the file cannot be repackaged
	 */
	public void repackage(Libraries libraries) throws IOException {
		repackage(getSource(), libraries);
	}

	/**
	 * Repackage to the given destination so that it can be launched using '
	 * {@literal java -jar}'.
	 * @param destination the destination file (may be the same as the source)
	 * @param libraries the libraries required to run the archive
	 * @throws IOException if the file cannot be repackaged
	 */
	public void repackage(File destination, Libraries libraries) throws IOException {
		repackage(destination, libraries, null);
	}

	/**
	 * Repackage to the given destination so that it can be launched using '
	 * {@literal java -jar}'.
	 * @param destination the destination file (may be the same as the source)
	 * @param libraries the libraries required to run the archive
	 * @param launchScript an optional launch script prepended to the front of the jar
	 * @throws IOException if the file cannot be repackaged
	 * @since 1.3.0
	 */
	public void repackage(File destination, Libraries libraries, LaunchScript launchScript) throws IOException {
		repackage(destination, libraries, launchScript, null);
	}

	/**
	 * Repackage to the given destination so that it can be launched using '
	 * {@literal java -jar}'.
	 * @param destination the destination file (may be the same as the source)
	 * @param libraries the libraries required to run the archive
	 * @param launchScript an optional launch script prepended to the front of the jar
	 * @param lastModifiedTime an optional last modified time to apply to the archive and
	 * its contents
	 * @throws IOException if the file cannot be repackaged
	 * @since 2.3.0
	 */
	public void repackage(File destination, Libraries libraries, LaunchScript launchScript, FileTime lastModifiedTime)
			throws IOException {
		Assert.isTrue(destination != null && !destination.isDirectory(), "Invalid destination");
		Layout layout = getLayout(); // get layout early
		if (lastModifiedTime != null && layout instanceof War) {
			throw new IllegalStateException("Reproducible repackaging is not supported with war packaging");
		}
		destination = destination.getAbsoluteFile();
		File source = getSource();
		if (isAlreadyPackaged() && source.equals(destination)) {
			return;
		}
		File workingSource = source;
		if (source.equals(destination)) {
			workingSource = getBackupFile();
			workingSource.delete();
			renameFile(source, workingSource);
		}
		destination.delete();
		try {
			try (JarFile sourceJar = new JarFile(workingSource)) {
				repackage(sourceJar, destination, libraries, launchScript, lastModifiedTime);
			}
		}
		finally {
			if (!this.backupSource && !source.equals(workingSource)) {
				deleteFile(workingSource);
			}
		}
	}

	private void repackage(JarFile sourceJar, File destination, Libraries libraries, LaunchScript launchScript,
			FileTime lastModifiedTime) throws IOException {
		try (JarWriter writer = new JarWriter(destination, launchScript, lastModifiedTime)) {
			write(sourceJar, libraries, writer);
		}
		if (lastModifiedTime != null) {
			destination.setLastModified(lastModifiedTime.toMillis());
		}
	}

	private void renameFile(File file, File dest) {
		if (!file.renameTo(dest)) {
			throw new IllegalStateException("Unable to rename '" + file + "' to '" + dest + "'");
		}
	}

	private void deleteFile(File file) {
		if (!file.delete()) {
			throw new IllegalStateException("Unable to delete '" + file + "'");
		}
	}

}
