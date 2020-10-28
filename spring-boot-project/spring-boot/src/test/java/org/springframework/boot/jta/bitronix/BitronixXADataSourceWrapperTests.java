package org.springframework.boot.jta.bitronix;

import javax.sql.DataSource;
import javax.sql.XADataSource;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;

/**
 * Tests for {@link BitronixXADataSourceWrapper}.
 *

 */
@Deprecated
class BitronixXADataSourceWrapperTests {

	@Test
	void wrap() throws Exception {
		XADataSource dataSource = mock(XADataSource.class);
		BitronixXADataSourceWrapper wrapper = new BitronixXADataSourceWrapper();
		DataSource wrapped = wrapper.wrapDataSource(dataSource);
		assertThat(wrapped).isInstanceOf(PoolingDataSourceBean.class);
		assertThat(((PoolingDataSourceBean) wrapped).getDataSource()).isSameAs(dataSource);
	}

}
