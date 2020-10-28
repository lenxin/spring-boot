package org.springframework.boot.devtools.tests;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.function.Function;

import org.springframework.util.FileCopyUtils;

/**
 * Provides access to the contents of a file.
 *

 */
class FileContents {

	private final File file;

	FileContents(File file) {
		this.file = file;
	}

	String get() {
		return get(Function.identity());
	}

	<T> T get(Function<String, T> transformer) {
		if ((!this.file.exists()) || this.file.length() == 0) {
			return null;
		}
		try {
			return transformer.apply(FileCopyUtils.copyToString(new FileReader(this.file)));
		}
		catch (IOException ex) {
			throw new RuntimeException(ex);
		}
	}

	@Override
	public String toString() {
		return get();
	}

}
