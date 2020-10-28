package org.springframework.boot.jdbc.metadata;

import javax.sql.DataSource;

import org.apache.commons.dbcp2.BasicDataSource;

/**
 * {@link DataSourcePoolMetadata} for an Apache Commons DBCP2 {@link DataSource}.
 *

 * @since 2.0.0
 */
public class CommonsDbcp2DataSourcePoolMetadata extends AbstractDataSourcePoolMetadata<BasicDataSource> {

	public CommonsDbcp2DataSourcePoolMetadata(BasicDataSource dataSource) {
		super(dataSource);
	}

	@Override
	public Integer getActive() {
		return getDataSource().getNumActive();
	}

	@Override
	public Integer getIdle() {
		return getDataSource().getNumIdle();
	}

	@Override
	public Integer getMax() {
		return getDataSource().getMaxTotal();
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
		return getDataSource().getDefaultAutoCommit();
	}

}
