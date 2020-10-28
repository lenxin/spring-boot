package org.springframework.boot.autoconfigure.transaction;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.springframework.boot.util.LambdaSafe;
import org.springframework.transaction.PlatformTransactionManager;

/**
 * A collection of {@link PlatformTransactionManagerCustomizer}.
 *

 * @since 1.5.0
 */
public class TransactionManagerCustomizers {

	private final List<PlatformTransactionManagerCustomizer<?>> customizers;

	public TransactionManagerCustomizers(Collection<? extends PlatformTransactionManagerCustomizer<?>> customizers) {
		this.customizers = (customizers != null) ? new ArrayList<>(customizers) : Collections.emptyList();
	}

	@SuppressWarnings("unchecked")
	public void customize(PlatformTransactionManager transactionManager) {
		LambdaSafe.callbacks(PlatformTransactionManagerCustomizer.class, this.customizers, transactionManager)
				.withLogger(TransactionManagerCustomizers.class)
				.invoke((customizer) -> customizer.customize(transactionManager));
	}

}
