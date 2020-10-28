package org.springframework.boot.autoconfigure.data.ldap;

import java.lang.annotation.Annotation;

import org.springframework.boot.autoconfigure.data.AbstractRepositoryConfigurationSourceSupport;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.data.ldap.repository.config.EnableLdapRepositories;
import org.springframework.data.ldap.repository.config.LdapRepositoryConfigurationExtension;
import org.springframework.data.repository.config.RepositoryConfigurationExtension;

/**
 * {@link ImportBeanDefinitionRegistrar} used to auto-configure Spring Data LDAP
 * Repositories.
 *

 */
class LdapRepositoriesRegistrar extends AbstractRepositoryConfigurationSourceSupport {

	@Override
	protected Class<? extends Annotation> getAnnotation() {
		return EnableLdapRepositories.class;
	}

	@Override
	protected Class<?> getConfiguration() {
		return EnableLdapRepositoriesConfiguration.class;
	}

	@Override
	protected RepositoryConfigurationExtension getRepositoryConfigurationExtension() {
		return new LdapRepositoryConfigurationExtension();
	}

	@EnableLdapRepositories
	private static class EnableLdapRepositoriesConfiguration {

	}

}
