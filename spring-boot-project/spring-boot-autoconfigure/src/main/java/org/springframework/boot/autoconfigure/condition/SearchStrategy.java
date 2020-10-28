package org.springframework.boot.autoconfigure.condition;

/**
 * Some named search strategies for beans in the bean factory hierarchy.
 *

 * @since 1.0.0
 */
public enum SearchStrategy {

	/**
	 * Search only the current context.
	 */
	CURRENT,

	/**
	 * Search all ancestors, but not the current context.
	 */
	ANCESTORS,

	/**
	 * Search the entire hierarchy.
	 */
	ALL

}
