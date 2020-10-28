package org.springframework.boot.jta.bitronix;

import javax.jms.ConnectionFactory;
import javax.jms.XAConnectionFactory;

import org.springframework.boot.jms.XAConnectionFactoryWrapper;

/**
 * {@link XAConnectionFactoryWrapper} that uses a Bitronix
 * {@link PoolingConnectionFactoryBean} to wrap a {@link XAConnectionFactory}.
 *

 * @since 1.2.0
 * @deprecated since 2.3.0 as the Bitronix project is no longer being maintained
 */
@Deprecated
public class BitronixXAConnectionFactoryWrapper implements XAConnectionFactoryWrapper {

	@Override
	public ConnectionFactory wrapConnectionFactory(XAConnectionFactory connectionFactory) {
		PoolingConnectionFactoryBean pool = new PoolingConnectionFactoryBean();
		pool.setConnectionFactory(connectionFactory);
		return pool;
	}

}
