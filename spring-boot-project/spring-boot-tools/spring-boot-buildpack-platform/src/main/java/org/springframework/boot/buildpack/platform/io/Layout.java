package org.springframework.boot.buildpack.platform.io;

import java.io.IOException;

/**
 * Interface that can be used to write a file/directory layout.
 *

 * @since 2.3.0
 */
public interface Layout {

	/**
	 * Add a directory to the content.
	 * @param name the full name of the directory to add.
	 * @param owner the owner of the directory
	 * @throws IOException on IO error
	 */
	void directory(String name, Owner owner) throws IOException;

	/**
	 * Write a file to the content.
	 * @param name the full name of the file to add.
	 * @param owner the owner of the file
	 * @param content the content to add
	 * @throws IOException on IO error
	 */
	void file(String name, Owner owner, Content content) throws IOException;

}
