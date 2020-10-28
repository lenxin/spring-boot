package org.springframework.boot.autoconfigure.integration;

import javax.sql.DataSource;

import org.springframework.boot.jdbc.AbstractDataSourceInitializer;
import org.springframework.boot.jdbc.DataSourceInitializationMode;
import org.springframework.core.io.ResourceLoader;
import org.springframework.util.Assert;

/**
 * Initializer for Spring Integration schema.
 *

 * @since 2.0.0
 */
public class IntegrationDataSourceInitializer extends AbstractDataSourceInitializer {

	private final IntegrationProperties.Jdbc properties;

	public IntegrationDataSourceInitializer(DataSource dataSource, ResourceLoader resourceLoader,
			IntegrationProperties properties) {
		super(dataSource, resourceLoader);
		Assert.notNull(properties, "IntegrationProperties must not be null");
		this.properties = properties.getJdbc();
	}

	@Override
	protected DataSourceInitializationMode getMode() {
		return this.properties.getInitializeSchema();
	}

	@Override
	protected String getSchemaLocation() {
		return this.properties.getSchema();
	}

}
