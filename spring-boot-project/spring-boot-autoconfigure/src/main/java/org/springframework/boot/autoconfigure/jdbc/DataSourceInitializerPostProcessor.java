package org.springframework.boot.autoconfigure.jdbc;

import javax.sql.DataSource;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.core.Ordered;

/**
 * {@link BeanPostProcessor} used to ensure that {@link DataSourceInitializer} is
 * initialized as soon as a {@link DataSource} is.
 *

 */
class DataSourceInitializerPostProcessor implements BeanPostProcessor, Ordered, BeanFactoryAware {

	@Override
	public int getOrder() {
		return Ordered.HIGHEST_PRECEDENCE + 1;
	}

	private BeanFactory beanFactory;

	@Override
	public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
		return bean;
	}

	@Override
	public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
		if (bean instanceof DataSource) {
			// force initialization of this bean as soon as we see a DataSource
			this.beanFactory.getBean(DataSourceInitializerInvoker.class);
		}
		return bean;
	}

	@Override
	public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
		this.beanFactory = beanFactory;
	}

}
