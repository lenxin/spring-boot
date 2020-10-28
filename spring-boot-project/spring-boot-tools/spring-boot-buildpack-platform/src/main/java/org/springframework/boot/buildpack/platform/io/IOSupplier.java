package org.springframework.boot.buildpack.platform.io;

import java.io.IOException;

/**
 * Supplier that can safely throw {@link IOException IO exceptions}.
 *
 * @param <T> the supplied type

 * @since 2.3.0
 */
@FunctionalInterface
public interface IOSupplier<T> {

	/**
	 * Gets the supplied value.
	 * @return the supplied value
	 * @throws IOException on IO error
	 */
	T get() throws IOException;

}
