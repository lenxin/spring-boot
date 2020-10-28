package org.springframework.boot.autoconfigure.quartz;

import javax.sql.DataSource;

import org.springframework.boot.jdbc.AbstractDataSourceInitializer;
import org.springframework.boot.jdbc.DataSourceInitializationMode;
import org.springframework.core.io.ResourceLoader;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;
import org.springframework.util.Assert;

/**
 * Initialize the Quartz Scheduler schema.
 *

 * @since 2.0.0
 */
public class QuartzDataSourceInitializer extends AbstractDataSourceInitializer {

	private final QuartzProperties properties;

	public QuartzDataSourceInitializer(DataSource dataSource, ResourceLoader resourceLoader,
			QuartzProperties properties) {
		super(dataSource, resourceLoader);
		Assert.notNull(properties, "QuartzProperties must not be null");
		this.properties = properties;
	}

	@Override
	protected void customize(ResourceDatabasePopulator populator) {
		populator.setCommentPrefixes(this.properties.getJdbc().getCommentPrefix().toArray(new String[0]));
	}

	@Override
	protected DataSourceInitializationMode getMode() {
		return this.properties.getJdbc().getInitializeSchema();
	}

	@Override
	protected String getSchemaLocation() {
		return this.properties.getJdbc().getSchema();
	}

	@Override
	protected String getDatabaseName() {
		String databaseName = super.getDatabaseName();
		if ("db2".equals(databaseName)) {
			return "db2_v95";
		}
		if ("mysql".equals(databaseName)) {
			return "mysql_innodb";
		}
		if ("postgresql".equals(databaseName)) {
			return "postgres";
		}
		if ("sqlserver".equals(databaseName)) {
			return "sqlServer";
		}
		return databaseName;
	}

}
