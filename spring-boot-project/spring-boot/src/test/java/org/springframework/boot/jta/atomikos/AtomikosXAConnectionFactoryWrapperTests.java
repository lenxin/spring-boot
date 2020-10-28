package org.springframework.boot.jta.atomikos;

import javax.jms.ConnectionFactory;
import javax.jms.XAConnectionFactory;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;

/**
 * Tests for {@link AtomikosXAConnectionFactoryWrapper}.
 *

 */
class AtomikosXAConnectionFactoryWrapperTests {

	@Test
	void wrap() {
		XAConnectionFactory connectionFactory = mock(XAConnectionFactory.class);
		AtomikosXAConnectionFactoryWrapper wrapper = new AtomikosXAConnectionFactoryWrapper();
		ConnectionFactory wrapped = wrapper.wrapConnectionFactory(connectionFactory);
		assertThat(wrapped).isInstanceOf(AtomikosConnectionFactoryBean.class);
		assertThat(((AtomikosConnectionFactoryBean) wrapped).getXaConnectionFactory()).isSameAs(connectionFactory);
	}

}
