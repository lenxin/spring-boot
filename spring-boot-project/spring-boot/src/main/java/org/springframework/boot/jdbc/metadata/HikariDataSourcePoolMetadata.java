package org.springframework.boot.jdbc.metadata;

import javax.sql.DataSource;

import com.zaxxer.hikari.HikariDataSource;
import com.zaxxer.hikari.pool.HikariPool;

import org.springframework.beans.DirectFieldAccessor;

/**
 * {@link DataSourcePoolMetadata} for a Hikari {@link DataSource}.
 *

 * @since 2.0.0
 */
public class HikariDataSourcePoolMetadata extends AbstractDataSourcePoolMetadata<HikariDataSource> {

	public HikariDataSourcePoolMetadata(HikariDataSource dataSource) {
		super(dataSource);
	}

	@Override
	public Integer getActive() {
		try {
			return getHikariPool().getActiveConnections();
		}
		catch (Exception ex) {
			return null;
		}
	}

	@Override
	public Integer getIdle() {
		try {
			return getHikariPool().getIdleConnections();
		}
		catch (Exception ex) {
			return null;
		}
	}

	private HikariPool getHikariPool() {
		return (HikariPool) new DirectFieldAccessor(getDataSource()).getPropertyValue("pool");
	}

	@Override
	public Integer getMax() {
		return getDataSource().getMaximumPoolSize();
	}

	@Override
	public Integer getMin() {
		return getDataSource().getMinimumIdle();
	}

	@Override
	public String getValidationQuery() {
		return getDataSource().getConnectionTestQuery();
	}

	@Override
	public Boolean getDefaultAutoCommit() {
		return getDataSource().isAutoCommit();
	}

}
