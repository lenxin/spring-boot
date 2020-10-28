package org.springframework.boot.jta.bitronix;

import javax.jms.ConnectionFactory;
import javax.jms.XAConnectionFactory;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;

/**
 * Tests for {@link BitronixXAConnectionFactoryWrapper}.
 *

 */
@Deprecated
class BitronixXAConnectionFactoryWrapperTests {

	@Test
	void wrap() {
		XAConnectionFactory connectionFactory = mock(XAConnectionFactory.class);
		BitronixXAConnectionFactoryWrapper wrapper = new BitronixXAConnectionFactoryWrapper();
		ConnectionFactory wrapped = wrapper.wrapConnectionFactory(connectionFactory);
		assertThat(wrapped).isInstanceOf(PoolingConnectionFactoryBean.class);
		assertThat(((PoolingConnectionFactoryBean) wrapped).getConnectionFactory()).isSameAs(connectionFactory);
	}

}
