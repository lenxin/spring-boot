package org.springframework.boot.jta.atomikos;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.verify;

/**
 * Tests for {@link AtomikosConnectionFactoryBean}.
 *

 */
class AtomikosConnectionFactoryBeanTests {

	@Test
	void beanMethods() throws Exception {
		MockAtomikosConnectionFactoryBean bean = spy(new MockAtomikosConnectionFactoryBean());
		bean.setBeanName("bean");
		bean.afterPropertiesSet();
		assertThat(bean.getUniqueResourceName()).isEqualTo("bean");
		verify(bean).init();
		verify(bean, never()).close();
		bean.destroy();
		verify(bean).close();
	}

	@SuppressWarnings("serial")
	static class MockAtomikosConnectionFactoryBean extends AtomikosConnectionFactoryBean {

		@Override
		public synchronized void init() {
		}

		@Override
		public synchronized void close() {
		}

	}

}
