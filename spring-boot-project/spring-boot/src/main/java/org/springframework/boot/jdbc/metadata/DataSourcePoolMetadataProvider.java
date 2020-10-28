package org.springframework.boot.jdbc.metadata;

import javax.sql.DataSource;

/**
 * Provide a {@link DataSourcePoolMetadata} based on a {@link DataSource}.
 *

 * @since 2.0.0
 */
@FunctionalInterface
public interface DataSourcePoolMetadataProvider {

	/**
	 * Return the {@link DataSourcePoolMetadata} instance able to manage the specified
	 * {@link DataSource} or {@code null} if the given data source could not be handled.
	 * @param dataSource the data source
	 * @return the data source pool metadata
	 */
	DataSourcePoolMetadata getDataSourcePoolMetadata(DataSource dataSource);

}
