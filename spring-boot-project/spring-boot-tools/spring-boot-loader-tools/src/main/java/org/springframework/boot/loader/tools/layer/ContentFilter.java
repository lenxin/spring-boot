package org.springframework.boot.loader.tools.layer;

/**
 * Callback interface that can be used to filter layer contents.
 *


 * @param <T> the content type
 * @since 2.3.0
 */
@FunctionalInterface
public interface ContentFilter<T> {

	/**
	 * Return if the filter matches the specified item.
	 * @param item the item to test
	 * @return if the filter matches
	 */
	boolean matches(T item);

}
