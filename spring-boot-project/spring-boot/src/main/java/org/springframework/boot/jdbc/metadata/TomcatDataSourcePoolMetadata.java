package org.springframework.boot.jdbc.metadata;

import org.apache.tomcat.jdbc.pool.ConnectionPool;
import org.apache.tomcat.jdbc.pool.DataSource;

/**
 * {@link DataSourcePoolMetadata} for a Tomcat DataSource.
 *

 * @since 2.0.0
 */
public class TomcatDataSourcePoolMetadata extends AbstractDataSourcePoolMetadata<DataSource> {

	public TomcatDataSourcePoolMetadata(DataSource dataSource) {
		super(dataSource);
	}

	@Override
	public Integer getActive() {
		ConnectionPool pool = getDataSource().getPool();
		return (pool != null) ? pool.getActive() : 0;
	}

	@Override
	public Integer getIdle() {
		return getDataSource().getNumIdle();
	}

	@Override
	public Integer getMax() {
		return getDataSource().getMaxActive();
	}

	@Override
	public Integer getMin() {
		return getDataSource().getMinIdle();
	}

	@Override
	public String getValidationQuery() {
		return getDataSource().getValidationQuery();
	}

	@Override
	public Boolean getDefaultAutoCommit() {
		return getDataSource().isDefaultAutoCommit();
	}

}
