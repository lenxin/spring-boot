package org.springframework.boot.cli.compiler.grape;

/**
 * Reports progress on a dependency resolution operation.
 *

 */
@FunctionalInterface
interface ProgressReporter {

	/**
	 * Notification that the operation has completed.
	 */
	void finished();

}
