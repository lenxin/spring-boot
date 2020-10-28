package org.springframework.boot.jta.atomikos;

import javax.jms.ConnectionFactory;
import javax.jms.XAConnectionFactory;

import org.springframework.boot.jms.XAConnectionFactoryWrapper;

/**
 * {@link XAConnectionFactoryWrapper} that uses an {@link AtomikosConnectionFactoryBean}
 * to wrap a {@link XAConnectionFactory}.
 *

 * @since 1.2.0
 */
public class AtomikosXAConnectionFactoryWrapper implements XAConnectionFactoryWrapper {

	@Override
	public ConnectionFactory wrapConnectionFactory(XAConnectionFactory connectionFactory) {
		AtomikosConnectionFactoryBean bean = new AtomikosConnectionFactoryBean();
		bean.setXaConnectionFactory(connectionFactory);
		return bean;
	}

}
