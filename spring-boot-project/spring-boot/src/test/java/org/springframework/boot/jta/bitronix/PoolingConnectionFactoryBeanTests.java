package org.springframework.boot.jta.bitronix;

import javax.jms.XAConnectionFactory;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

/**
 * Tests for {@link PoolingConnectionFactoryBean}.
 *

 */
@Deprecated
class PoolingConnectionFactoryBeanTests {

	@SuppressWarnings("serial")
	private PoolingConnectionFactoryBean bean = new PoolingConnectionFactoryBean() {
		@Override
		public synchronized void init() {
			// Stub out for the tests
		}
	};

	@Test
	void sensibleDefaults() {
		assertThat(this.bean.getMaxPoolSize()).isEqualTo(10);
		assertThat(this.bean.getTestConnections()).isTrue();
		assertThat(this.bean.getAutomaticEnlistingEnabled()).isTrue();
		assertThat(this.bean.getAllowLocalTransactions()).isTrue();
	}

	@Test
	void setsUniqueNameIfNull() throws Exception {
		this.bean.setBeanName("beanName");
		this.bean.afterPropertiesSet();
		assertThat(this.bean.getUniqueName()).isEqualTo("beanName");
	}

	@Test
	void doesNotSetUniqueNameIfNotNull() throws Exception {
		this.bean.setBeanName("beanName");
		this.bean.setUniqueName("un");
		this.bean.afterPropertiesSet();
		assertThat(this.bean.getUniqueName()).isEqualTo("un");
	}

	@Test
	void setConnectionFactory() throws Exception {
		XAConnectionFactory factory = mock(XAConnectionFactory.class);
		this.bean.setConnectionFactory(factory);
		this.bean.setBeanName("beanName");
		this.bean.afterPropertiesSet();
		this.bean.init();
		this.bean.createPooledConnection(factory, this.bean);
		verify(factory).createXAConnection();
	}

}
