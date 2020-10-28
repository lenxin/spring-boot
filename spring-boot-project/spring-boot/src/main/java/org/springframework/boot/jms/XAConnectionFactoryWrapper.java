package org.springframework.boot.jms;

import javax.jms.ConnectionFactory;
import javax.jms.XAConnectionFactory;
import javax.transaction.TransactionManager;

/**
 * Strategy interface used to wrap a JMS {@link XAConnectionFactory} enrolling it with a
 * JTA {@link TransactionManager}.
 *

 * @since 2.0.0
 */
@FunctionalInterface
public interface XAConnectionFactoryWrapper {

	/**
	 * Wrap the specific {@link XAConnectionFactory} and enroll it with a JTA
	 * {@link TransactionManager}.
	 * @param connectionFactory the connection factory to wrap
	 * @return the wrapped connection factory
	 * @throws Exception if the connection factory cannot be wrapped
	 */
	ConnectionFactory wrapConnectionFactory(XAConnectionFactory connectionFactory) throws Exception;

}
