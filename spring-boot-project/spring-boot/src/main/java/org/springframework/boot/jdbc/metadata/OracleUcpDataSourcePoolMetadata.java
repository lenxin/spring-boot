package org.springframework.boot.jdbc.metadata;

import java.sql.SQLException;

import javax.sql.DataSource;

import oracle.ucp.jdbc.PoolDataSource;

import org.springframework.util.StringUtils;

/**
 * {@link DataSourcePoolMetadata} for an Oracle UCP {@link DataSource}.
 *

 * @since 2.4.0
 */
public class OracleUcpDataSourcePoolMetadata extends AbstractDataSourcePoolMetadata<PoolDataSource> {

	public OracleUcpDataSourcePoolMetadata(PoolDataSource dataSource) {
		super(dataSource);
	}

	@Override
	public Integer getActive() {
		try {
			return getDataSource().getBorrowedConnectionsCount();
		}
		catch (SQLException ex) {
			return null;
		}
	}

	@Override
	public Integer getIdle() {
		try {
			return getDataSource().getAvailableConnectionsCount();
		}
		catch (SQLException ex) {
			return null;
		}
	}

	@Override
	public Integer getMax() {
		return getDataSource().getMaxPoolSize();
	}

	@Override
	public Integer getMin() {
		return getDataSource().getMinPoolSize();
	}

	@Override
	public String getValidationQuery() {
		return getDataSource().getSQLForValidateConnection();
	}

	@Override
	public Boolean getDefaultAutoCommit() {
		String autoCommit = getDataSource().getConnectionProperty("autoCommit");
		return StringUtils.hasText(autoCommit) ? Boolean.valueOf(autoCommit) : null;
	}

}
