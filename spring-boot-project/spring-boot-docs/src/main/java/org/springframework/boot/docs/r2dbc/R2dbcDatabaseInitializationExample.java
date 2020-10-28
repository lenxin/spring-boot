package org.springframework.boot.docs.r2dbc;

import io.r2dbc.spi.ConnectionFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.r2dbc.connection.init.ResourceDatabasePopulator;

/**
 * Example configuration for initializing a database using R2DBC.
 *

 */
public class R2dbcDatabaseInitializationExample {

	// tag::configuration[]
	@Configuration(proxyBeanMethods = false)
	static class DatabaseInitializationConfiguration {

		@Autowired
		void initializeDatabase(ConnectionFactory connectionFactory) {
			ResourceLoader resourceLoader = new DefaultResourceLoader();
			Resource[] scripts = new Resource[] { resourceLoader.getResource("classpath:schema.sql"),
					resourceLoader.getResource("classpath:data.sql") };
			new ResourceDatabasePopulator(scripts).populate(connectionFactory).block();
		}

	}
	// end::configuration[]

}
