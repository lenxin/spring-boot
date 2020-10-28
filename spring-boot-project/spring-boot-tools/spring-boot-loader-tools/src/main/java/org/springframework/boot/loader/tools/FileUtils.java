package org.springframework.boot.loader.tools;

import java.io.File;
import java.io.IOException;

/**
 * Utilities for manipulating files and directories in Spring Boot tooling.
 *


 * @since 1.0.0
 */
public abstract class FileUtils {

	/**
	 * Utility to remove duplicate files from an "output" directory if they already exist
	 * in an "origin". Recursively scans the origin directory looking for files (not
	 * directories) that exist in both places and deleting the copy.
	 * @param outputDirectory the output directory
	 * @param originDirectory the origin directory
	 */
	public static void removeDuplicatesFromOutputDirectory(File outputDirectory, File originDirectory) {
		if (originDirectory.isDirectory()) {
			for (String name : originDirectory.list()) {
				File targetFile = new File(outputDirectory, name);
				if (targetFile.exists() && targetFile.canWrite()) {
					if (!targetFile.isDirectory()) {
						targetFile.delete();
					}
					else {
						FileUtils.removeDuplicatesFromOutputDirectory(targetFile, new File(originDirectory, name));
					}
				}
			}
		}
	}

	/**
	 * Generate a SHA.1 Hash for a given file.
	 * @param file the file to hash
	 * @return the hash value as a String
	 * @throws IOException if the file cannot be read
	 */
	public static String sha1Hash(File file) throws IOException {
		return Digest.sha1(InputStreamSupplier.forFile(file));
	}

}
