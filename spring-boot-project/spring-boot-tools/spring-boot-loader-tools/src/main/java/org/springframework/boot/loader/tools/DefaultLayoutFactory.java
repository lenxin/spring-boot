package org.springframework.boot.loader.tools;

import java.io.File;

/**
 * Default implementation of {@link LayoutFactory}.
 *

 * @since 1.5.0
 */
public class DefaultLayoutFactory implements LayoutFactory {

	@Override
	public Layout getLayout(File source) {
		return Layouts.forFile(source);
	}

}
