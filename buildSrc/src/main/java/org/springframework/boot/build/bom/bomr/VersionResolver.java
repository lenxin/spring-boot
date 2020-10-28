package org.springframework.boot.build.bom.bomr;

import java.util.SortedSet;

import org.springframework.boot.build.bom.bomr.version.DependencyVersion;

/**
 * Resolves the available versions for a module.
 *

 */
interface VersionResolver {

	/**
	 * Resolves the available versions for the module identified by the given
	 * {@code groupId} and {@code artifactId}.
	 * @param groupId module's group ID
	 * @param artifactId module's artifact ID
	 * @return the available versions
	 */
	SortedSet<DependencyVersion> resolveVersions(String groupId, String artifactId);

}
