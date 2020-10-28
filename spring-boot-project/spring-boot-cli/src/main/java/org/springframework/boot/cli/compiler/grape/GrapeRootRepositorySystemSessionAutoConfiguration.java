package org.springframework.boot.cli.compiler.grape;

import java.io.File;

import org.eclipse.aether.DefaultRepositorySystemSession;
import org.eclipse.aether.RepositorySystem;
import org.eclipse.aether.repository.LocalRepository;
import org.eclipse.aether.repository.LocalRepositoryManager;

import org.springframework.util.StringUtils;

/**
 * Honours the configuration of {@code grape.root} by customizing the session's local
 * repository location.
 *

 * @since 1.2.5
 */
public class GrapeRootRepositorySystemSessionAutoConfiguration implements RepositorySystemSessionAutoConfiguration {

	@Override
	public void apply(DefaultRepositorySystemSession session, RepositorySystem repositorySystem) {
		String grapeRoot = System.getProperty("grape.root");
		if (StringUtils.hasLength(grapeRoot)) {
			configureLocalRepository(session, repositorySystem, grapeRoot);
		}
	}

	private void configureLocalRepository(DefaultRepositorySystemSession session, RepositorySystem repositorySystem,
			String grapeRoot) {
		File repositoryDir = new File(grapeRoot, "repository");
		LocalRepository localRepository = new LocalRepository(repositoryDir);
		LocalRepositoryManager localRepositoryManager = repositorySystem.newLocalRepositoryManager(session,
				localRepository);
		session.setLocalRepositoryManager(localRepositoryManager);
	}

}
