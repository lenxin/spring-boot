package org.springframework.boot.actuate.health;

/**
 * Strategy used to map a {@link Status health status} to an HTTP status code.
 *


 * @since 2.2.0
 */
@FunctionalInterface
public interface HttpCodeStatusMapper {

	/**
	 * A {@link HttpCodeStatusMapper} instance using default mappings.
	 * @since 2.3.0
	 */
	HttpCodeStatusMapper DEFAULT = new SimpleHttpCodeStatusMapper();

	/**
	 * Return the HTTP status code that corresponds to the given {@link Status health
	 * status}.
	 * @param status the health status to map
	 * @return the corresponding HTTP status code
	 */
	int getStatusCode(Status status);

}
