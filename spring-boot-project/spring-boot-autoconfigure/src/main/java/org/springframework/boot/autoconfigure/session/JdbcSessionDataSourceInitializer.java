package org.springframework.boot.autoconfigure.session;

import javax.sql.DataSource;

import org.springframework.boot.jdbc.AbstractDataSourceInitializer;
import org.springframework.boot.jdbc.DataSourceInitializationMode;
import org.springframework.core.io.ResourceLoader;
import org.springframework.util.Assert;

/**
 * Initializer for Spring Session schema.
 *

 * @since 1.4.0
 */
public class JdbcSessionDataSourceInitializer extends AbstractDataSourceInitializer {

	private final JdbcSessionProperties properties;

	public JdbcSessionDataSourceInitializer(DataSource dataSource, ResourceLoader resourceLoader,
			JdbcSessionProperties properties) {
		super(dataSource, resourceLoader);
		Assert.notNull(properties, "JdbcSessionProperties must not be null");
		this.properties = properties;
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
