package org.springframework.boot.web.server;

/**
 * Interface for a registry that holds {@link ErrorPage ErrorPages}.
 *

 * @since 2.0.0
 */
@FunctionalInterface
public interface ErrorPageRegistry {

	/**
	 * Adds error pages that will be used when handling exceptions.
	 * @param errorPages the error pages
	 */
	void addErrorPages(ErrorPage... errorPages);

}
