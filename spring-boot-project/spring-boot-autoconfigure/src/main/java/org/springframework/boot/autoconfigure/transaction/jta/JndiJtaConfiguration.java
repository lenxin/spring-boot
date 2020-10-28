package org.springframework.boot.autoconfigure.transaction.jta;

import org.springframework.beans.factory.ObjectProvider;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnJndi;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.transaction.TransactionManagerCustomizers;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.config.JtaTransactionManagerFactoryBean;
import org.springframework.transaction.jta.JtaTransactionManager;

/**
 * JTA Configuration for a JNDI-managed {@link JtaTransactionManager}.
 *



 */
@Configuration(proxyBeanMethods = false)
@ConditionalOnClass(JtaTransactionManager.class)
@ConditionalOnJndi({ JtaTransactionManager.DEFAULT_USER_TRANSACTION_NAME, "java:comp/TransactionManager",
		"java:appserver/TransactionManager", "java:pm/TransactionManager", "java:/TransactionManager" })
@ConditionalOnMissingBean(org.springframework.transaction.TransactionManager.class)
class JndiJtaConfiguration {

	@Bean
	JtaTransactionManager transactionManager(
			ObjectProvider<TransactionManagerCustomizers> transactionManagerCustomizers) {
		JtaTransactionManager jtaTransactionManager = new JtaTransactionManagerFactoryBean().getObject();
		transactionManagerCustomizers.ifAvailable((customizers) -> customizers.customize(jtaTransactionManager));
		return jtaTransactionManager;
	}

}
