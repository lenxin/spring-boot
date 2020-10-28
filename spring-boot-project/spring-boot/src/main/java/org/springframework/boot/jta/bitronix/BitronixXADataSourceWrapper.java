package org.springframework.boot.jta.bitronix;

import javax.sql.XADataSource;

import org.springframework.boot.jdbc.XADataSourceWrapper;

/**
 * {@link XADataSourceWrapper} that uses a Bitronix {@link PoolingDataSourceBean} to wrap
 * a {@link XADataSource}.
 *

 * @since 1.2.0
 * @deprecated since 2.3.0 as the Bitronix project is no longer being maintained
 */
@Deprecated
public class BitronixXADataSourceWrapper implements XADataSourceWrapper {

	@Override
	public PoolingDataSourceBean wrapDataSource(XADataSource dataSource) throws Exception {
		PoolingDataSourceBean pool = new PoolingDataSourceBean();
		pool.setDataSource(dataSource);
		return pool;
	}

}
