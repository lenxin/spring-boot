package org.springframework.boot.origin;

/**
 * Interface to provide access to the origin of an item.
 *

 * @since 2.0.0
 * @see Origin
 */
@FunctionalInterface
public interface OriginProvider {

	/**
	 * Return the source origin or {@code null} if the origin is not known.
	 * @return the origin or {@code null}
	 */
	Origin getOrigin();

}
