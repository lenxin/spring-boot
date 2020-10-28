package org.springframework.boot.autoconfigure.jooq;

import org.jooq.Transaction;

import org.springframework.transaction.TransactionStatus;

/**
 * Adapts a Spring transaction for JOOQ.
 *



 */
class SpringTransaction implements Transaction {

	// Based on the jOOQ-spring-example from https://github.com/jOOQ/jOOQ

	private final TransactionStatus transactionStatus;

	SpringTransaction(TransactionStatus transactionStatus) {
		this.transactionStatus = transactionStatus;
	}

	TransactionStatus getTxStatus() {
		return this.transactionStatus;
	}

}
