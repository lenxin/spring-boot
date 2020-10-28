package org.springframework.boot.cli.command.install;

import java.io.File;
import java.util.List;

/**
 * Resolve artifact identifiers (typically in the form {@literal group:artifact:version})
 * to {@link File}s.
 *

 */
@FunctionalInterface
interface DependencyResolver {

	/**
	 * Resolves the given {@code artifactIdentifiers}, typically in the form
	 * "group:artifact:version", and their dependencies.
	 * @param artifactIdentifiers the artifacts to resolve
	 * @return the {@code File}s for the resolved artifacts
	 * @throws Exception if dependency resolution fails
	 */
	List<File> resolve(List<String> artifactIdentifiers) throws Exception;

}
