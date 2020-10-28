package org.springframework.boot.jdbc;

import javax.sql.DataSource;
import javax.sql.XADataSource;
import javax.transaction.TransactionManager;

/**
 * Strategy interface used to wrap an {@link XADataSource} enrolling it with a JTA
 * {@link TransactionManager}.
 *

 * @since 2.0.0
 */
@FunctionalInterface
public interface XADataSourceWrapper {

	/**
	 * Wrap the specific {@link XADataSource} and enroll it with a JTA
	 * {@link TransactionManager}.
	 * @param dataSource the data source to wrap
	 * @return the wrapped data source
	 * @throws Exception if the data source cannot be wrapped
	 */
	DataSource wrapDataSource(XADataSource dataSource) throws Exception;

}
