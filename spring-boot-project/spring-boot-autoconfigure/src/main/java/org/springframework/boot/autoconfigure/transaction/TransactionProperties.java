package org.springframework.boot.autoconfigure.transaction;

import java.time.Duration;
import java.time.temporal.ChronoUnit;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.convert.DurationUnit;
import org.springframework.transaction.support.AbstractPlatformTransactionManager;

/**
 * Configuration properties that can be applied to an
 * {@link AbstractPlatformTransactionManager}.
 *


 * @since 1.5.0
 */
@ConfigurationProperties(prefix = "spring.transaction")
public class TransactionProperties implements PlatformTransactionManagerCustomizer<AbstractPlatformTransactionManager> {

	/**
	 * Default transaction timeout. If a duration suffix is not specified, seconds will be
	 * used.
	 */
	@DurationUnit(ChronoUnit.SECONDS)
	private Duration defaultTimeout;

	/**
	 * Whether to roll back on commit failures.
	 */
	private Boolean rollbackOnCommitFailure;

	public Duration getDefaultTimeout() {
		return this.defaultTimeout;
	}

	public void setDefaultTimeout(Duration defaultTimeout) {
		this.defaultTimeout = defaultTimeout;
	}

	public Boolean getRollbackOnCommitFailure() {
		return this.rollbackOnCommitFailure;
	}

	public void setRollbackOnCommitFailure(Boolean rollbackOnCommitFailure) {
		this.rollbackOnCommitFailure = rollbackOnCommitFailure;
	}

	@Override
	public void customize(AbstractPlatformTransactionManager transactionManager) {
		if (this.defaultTimeout != null) {
			transactionManager.setDefaultTimeout((int) this.defaultTimeout.getSeconds());
		}
		if (this.rollbackOnCommitFailure != null) {
			transactionManager.setRollbackOnCommitFailure(this.rollbackOnCommitFailure);
		}
	}

}
