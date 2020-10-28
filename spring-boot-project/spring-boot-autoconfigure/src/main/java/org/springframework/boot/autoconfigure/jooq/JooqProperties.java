package org.springframework.boot.autoconfigure.jooq;

import javax.sql.DataSource;

import org.jooq.SQLDialect;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * Configuration properties for the JOOQ database library.
 *


 * @since 1.3.0
 */
@ConfigurationProperties(prefix = "spring.jooq")
public class JooqProperties {

	/**
	 * SQL dialect to use. Auto-detected by default.
	 */
	private SQLDialect sqlDialect;

	public SQLDialect getSqlDialect() {
		return this.sqlDialect;
	}

	public void setSqlDialect(SQLDialect sqlDialect) {
		this.sqlDialect = sqlDialect;
	}

	/**
	 * Determine the {@link SQLDialect} to use based on this configuration and the primary
	 * {@link DataSource}.
	 * @param dataSource the data source
	 * @return the {@code SQLDialect} to use for that {@link DataSource}
	 */
	public SQLDialect determineSqlDialect(DataSource dataSource) {
		if (this.sqlDialect != null) {
			return this.sqlDialect;
		}
		return SqlDialectLookup.getDialect(dataSource);
	}

}
