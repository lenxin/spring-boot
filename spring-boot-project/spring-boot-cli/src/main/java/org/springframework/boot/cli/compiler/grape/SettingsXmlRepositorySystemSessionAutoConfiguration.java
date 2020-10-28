package org.springframework.boot.cli.compiler.grape;

import org.eclipse.aether.DefaultRepositorySystemSession;
import org.eclipse.aether.RepositorySystem;
import org.eclipse.aether.repository.LocalRepository;

import org.springframework.boot.cli.compiler.maven.MavenSettings;
import org.springframework.boot.cli.compiler.maven.MavenSettingsReader;

/**
 * Auto-configuration for a RepositorySystemSession that uses Maven's settings.xml to
 * determine the configuration settings.
 *

 * @since 1.0.0
 */
public class SettingsXmlRepositorySystemSessionAutoConfiguration implements RepositorySystemSessionAutoConfiguration {

	@Override
	public void apply(DefaultRepositorySystemSession session, RepositorySystem repositorySystem) {
		MavenSettings settings = getSettings(session);
		String localRepository = settings.getLocalRepository();
		if (localRepository != null) {
			session.setLocalRepositoryManager(
					repositorySystem.newLocalRepositoryManager(session, new LocalRepository(localRepository)));
		}
	}

	private MavenSettings getSettings(DefaultRepositorySystemSession session) {
		MavenSettings settings = new MavenSettingsReader().readSettings();
		session.setOffline(settings.getOffline());
		session.setMirrorSelector(settings.getMirrorSelector());
		session.setAuthenticationSelector(settings.getAuthenticationSelector());
		session.setProxySelector(settings.getProxySelector());
		return settings;
	}

}
