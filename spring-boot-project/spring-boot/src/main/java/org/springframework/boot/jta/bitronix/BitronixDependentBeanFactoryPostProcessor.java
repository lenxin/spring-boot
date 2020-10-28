package org.springframework.boot.jta.bitronix;

import javax.transaction.TransactionManager;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.core.Ordered;

/**
 * {@link BeanFactoryPostProcessor} to automatically register the recommended
 * {@link ConfigurableListableBeanFactory#registerDependentBean(String, String)
 * dependencies} for correct Bitronix shutdown ordering. With Bitronix it appears that
 * ConnectionFactory and DataSource beans must be shutdown before the
 * {@link TransactionManager}.
 *

 * @since 1.2.0
 * @deprecated since 2.3.0 as the Bitronix project is no longer being maintained
 */
@Deprecated
public class BitronixDependentBeanFactoryPostProcessor implements BeanFactoryPostProcessor, Ordered {

	private static final String[] NO_BEANS = {};

	private int order = Ordered.LOWEST_PRECEDENCE;

	@Override
	public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
		String[] transactionManagers = beanFactory.getBeanNamesForType(TransactionManager.class, true, false);
		for (String transactionManager : transactionManagers) {
			addTransactionManagerDependencies(beanFactory, transactionManager);
		}
	}

	private void addTransactionManagerDependencies(ConfigurableListableBeanFactory beanFactory,
			String transactionManager) {
		for (String dependentBeanName : getBeanNamesForType(beanFactory, "javax.jms.ConnectionFactory")) {
			beanFactory.registerDependentBean(transactionManager, dependentBeanName);
		}
		for (String dependentBeanName : getBeanNamesForType(beanFactory, "javax.sql.DataSource")) {
			beanFactory.registerDependentBean(transactionManager, dependentBeanName);
		}
	}

	private String[] getBeanNamesForType(ConfigurableListableBeanFactory beanFactory, String type) {
		try {
			return beanFactory.getBeanNamesForType(Class.forName(type), true, false);
		}
		catch (ClassNotFoundException | NoClassDefFoundError ex) {
			// Ignore
		}
		return NO_BEANS;
	}

	@Override
	public int getOrder() {
		return this.order;
	}

	public void setOrder(int order) {
		this.order = order;
	}

}
