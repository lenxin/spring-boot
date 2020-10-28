package org.springframework.boot.jta.atomikos;

import javax.sql.XADataSource;

import org.springframework.boot.jdbc.XADataSourceWrapper;

/**
 * {@link XADataSourceWrapper} that uses an {@link AtomikosDataSourceBean} to wrap a
 * {@link XADataSource}.
 *

 * @since 1.2.0
 */
public class AtomikosXADataSourceWrapper implements XADataSourceWrapper {

	@Override
	public AtomikosDataSourceBean wrapDataSource(XADataSource dataSource) throws Exception {
		AtomikosDataSourceBean bean = new AtomikosDataSourceBean();
		bean.setXaDataSource(dataSource);
		return bean;
	}

}
