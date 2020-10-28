package org.springframework.boot.autoconfigure.batch;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.springframework.boot.autoconfigure.transaction.TransactionManagerCustomizers;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;

/**
 * A {@link BasicBatchConfigurer} tailored for JPA.
 *

 * @since 2.0.0
 */
public class JpaBatchConfigurer extends BasicBatchConfigurer {

	private static final Log logger = LogFactory.getLog(JpaBatchConfigurer.class);

	private final EntityManagerFactory entityManagerFactory;

	/**
	 * Create a new {@link BasicBatchConfigurer} instance.
	 * @param properties the batch properties
	 * @param dataSource the underlying data source
	 * @param transactionManagerCustomizers transaction manager customizers (or
	 * {@code null})
	 * @param entityManagerFactory the entity manager factory (or {@code null})
	 */
	protected JpaBatchConfigurer(BatchProperties properties, DataSource dataSource,
			TransactionManagerCustomizers transactionManagerCustomizers, EntityManagerFactory entityManagerFactory) {
		super(properties, dataSource, transactionManagerCustomizers);
		this.entityManagerFactory = entityManagerFactory;
	}

	@Override
	protected String determineIsolationLevel() {
		logger.warn("JPA does not support custom isolation levels, so locks may not be taken when launching Jobs");
		return "ISOLATION_DEFAULT";
	}

	@Override
	protected PlatformTransactionManager createTransactionManager() {
		return new JpaTransactionManager(this.entityManagerFactory);
	}

}
