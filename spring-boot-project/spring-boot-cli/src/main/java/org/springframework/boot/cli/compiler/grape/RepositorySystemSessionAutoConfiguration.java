package org.springframework.boot.cli.compiler.grape;

import org.eclipse.aether.DefaultRepositorySystemSession;
import org.eclipse.aether.RepositorySystem;

/**
 * Strategy that can be used to apply some auto-configuration during the installation of
 * an {@link AetherGrapeEngine}.
 *

 * @since 1.0.0
 */
@FunctionalInterface
public interface RepositorySystemSessionAutoConfiguration {

	/**
	 * Apply the configuration.
	 * @param session the repository system session
	 * @param repositorySystem the repository system
	 */
	void apply(DefaultRepositorySystemSession session, RepositorySystem repositorySystem);

}
