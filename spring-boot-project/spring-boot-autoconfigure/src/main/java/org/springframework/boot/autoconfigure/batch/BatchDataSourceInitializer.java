package org.springframework.boot.autoconfigure.batch;

import javax.sql.DataSource;

import org.springframework.boot.jdbc.AbstractDataSourceInitializer;
import org.springframework.boot.jdbc.DataSourceInitializationMode;
import org.springframework.core.io.ResourceLoader;
import org.springframework.util.Assert;

/**
 * Initialize the Spring Batch schema (ignoring errors, so it should be idempotent).
 *


 * @since 1.0.0
 */
public class BatchDataSourceInitializer extends AbstractDataSourceInitializer {

	private final BatchProperties properties;

	public BatchDataSourceInitializer(DataSource dataSource, ResourceLoader resourceLoader,
			BatchProperties properties) {
		super(dataSource, resourceLoader);
		Assert.notNull(properties, "BatchProperties must not be null");
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

	@Override
	protected String getDatabaseName() {
		String databaseName = super.getDatabaseName();
		if ("oracle".equals(databaseName)) {
			return "oracle10g";
		}
		return databaseName;
	}

}
