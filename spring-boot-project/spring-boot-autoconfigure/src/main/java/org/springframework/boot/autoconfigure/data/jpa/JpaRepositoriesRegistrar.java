package org.springframework.boot.autoconfigure.data.jpa;

import java.lang.annotation.Annotation;
import java.util.Locale;

import org.springframework.boot.autoconfigure.data.AbstractRepositoryConfigurationSourceSupport;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.core.env.Environment;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.jpa.repository.config.JpaRepositoryConfigExtension;
import org.springframework.data.repository.config.BootstrapMode;
import org.springframework.data.repository.config.RepositoryConfigurationExtension;
import org.springframework.util.StringUtils;

/**
 * {@link ImportBeanDefinitionRegistrar} used to auto-configure Spring Data JPA
 * Repositories.
 *



 */
class JpaRepositoriesRegistrar extends AbstractRepositoryConfigurationSourceSupport {

	private BootstrapMode bootstrapMode = null;

	@Override
	protected Class<? extends Annotation> getAnnotation() {
		return EnableJpaRepositories.class;
	}

	@Override
	protected Class<?> getConfiguration() {
		return EnableJpaRepositoriesConfiguration.class;
	}

	@Override
	protected RepositoryConfigurationExtension getRepositoryConfigurationExtension() {
		return new JpaRepositoryConfigExtension();
	}

	@Override
	protected BootstrapMode getBootstrapMode() {
		return (this.bootstrapMode == null) ? BootstrapMode.DEFERRED : this.bootstrapMode;
	}

	@Override
	public void setEnvironment(Environment environment) {
		super.setEnvironment(environment);
		configureBootstrapMode(environment);
	}

	private void configureBootstrapMode(Environment environment) {
		String property = environment.getProperty("spring.data.jpa.repositories.bootstrap-mode");
		if (StringUtils.hasText(property)) {
			this.bootstrapMode = BootstrapMode.valueOf(property.toUpperCase(Locale.ENGLISH));
		}
	}

	@EnableJpaRepositories
	private static class EnableJpaRepositoriesConfiguration {

	}

}
