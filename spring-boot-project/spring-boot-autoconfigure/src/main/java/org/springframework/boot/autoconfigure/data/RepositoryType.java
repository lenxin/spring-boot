package org.springframework.boot.autoconfigure.data;

/**
 * Type of Spring Data repositories to enable.
 *

 * @since 2.0.0
 */
public enum RepositoryType {

	/**
	 * Enables all repository types automatically based on their availability.
	 */
	AUTO,

	/**
	 * Enables imperative repositories.
	 */
	IMPERATIVE,

	/**
	 * Enables no repositories.
	 */
	NONE,

	/**
	 * Enables reactive repositories.
	 */
	REACTIVE

}
