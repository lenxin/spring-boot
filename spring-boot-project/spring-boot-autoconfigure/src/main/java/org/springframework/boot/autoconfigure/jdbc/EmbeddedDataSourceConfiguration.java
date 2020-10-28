package org.springframework.boot.autoconfigure.jdbc;

import org.springframework.beans.factory.BeanClassLoaderAware;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabase;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;

/**
 * Configuration for embedded data sources.
 *


 * @since 1.0.0
 * @see DataSourceAutoConfiguration
 */
@Configuration(proxyBeanMethods = false)
@EnableConfigurationProperties(DataSourceProperties.class)
public class EmbeddedDataSourceConfiguration implements BeanClassLoaderAware {

	private ClassLoader classLoader;

	@Override
	public void setBeanClassLoader(ClassLoader classLoader) {
		this.classLoader = classLoader;
	}

	@Bean(destroyMethod = "shutdown")
	public EmbeddedDatabase dataSource(DataSourceProperties properties) {
		return new EmbeddedDatabaseBuilder().setType(EmbeddedDatabaseConnection.get(this.classLoader).getType())
				.setName(properties.determineDatabaseName()).build();
	}

}
