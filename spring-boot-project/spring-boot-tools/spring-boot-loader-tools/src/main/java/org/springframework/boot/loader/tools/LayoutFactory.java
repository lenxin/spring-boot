package org.springframework.boot.loader.tools;

import java.io.File;

/**
 * Factory interface used to create a {@link Layout}.
 *


 * @since 1.0.0
 */
@FunctionalInterface
public interface LayoutFactory {

	/**
	 * Return a {@link Layout} for the specified source file.
	 * @param source the source file
	 * @return the layout to use for the file
	 */
	Layout getLayout(File source);

}
