package org.springframework.boot.jdbc;

/**
 * Supported {@link javax.sql.DataSource} initialization modes.
 *


 * @since 2.0.0
 * @see AbstractDataSourceInitializer
 */
public enum DataSourceInitializationMode {

	/**
	 * Always initialize the datasource.
	 */
	ALWAYS,

	/**
	 * Only initialize an embedded datasource.
	 */
	EMBEDDED,

	/**
	 * Do not initialize the datasource.
	 */
	NEVER

}
