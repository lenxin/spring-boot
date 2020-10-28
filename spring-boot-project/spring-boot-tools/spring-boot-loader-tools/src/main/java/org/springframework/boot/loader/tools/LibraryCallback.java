package org.springframework.boot.loader.tools;

import java.io.File;
import java.io.IOException;

/**
 * Callback interface used to iterate {@link Libraries}.
 *

 * @since 1.0.0
 */
@FunctionalInterface
public interface LibraryCallback {

	/**
	 * Callback for a single library backed by a {@link File}.
	 * @param library the library
	 * @throws IOException if the operation fails
	 */
	void library(Library library) throws IOException;

}
