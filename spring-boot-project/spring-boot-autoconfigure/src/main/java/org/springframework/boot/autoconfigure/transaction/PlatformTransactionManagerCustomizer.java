package org.springframework.boot.autoconfigure.transaction;

import org.springframework.transaction.PlatformTransactionManager;

/**
 * Callback interface that can be implemented by beans wishing to customize
 * {@link PlatformTransactionManager PlatformTransactionManagers} whilst retaining default
 * auto-configuration.
 *
 * @param <T> the transaction manager type

 * @since 1.5.0
 */
@FunctionalInterface
public interface PlatformTransactionManagerCustomizer<T extends PlatformTransactionManager> {

	/**
	 * Customize the given transaction manager.
	 * @param transactionManager the transaction manager to customize
	 */
	void customize(T transactionManager);

}
