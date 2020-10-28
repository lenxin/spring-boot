package org.springframework.boot.jta.atomikos;

import javax.sql.DataSource;
import javax.sql.XADataSource;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;

/**
 * Tests for {@link AtomikosXADataSourceWrapper}.
 *

 */
class AtomikosXADataSourceWrapperTests {

	@Test
	void wrap() throws Exception {
		XADataSource dataSource = mock(XADataSource.class);
		AtomikosXADataSourceWrapper wrapper = new AtomikosXADataSourceWrapper();
		DataSource wrapped = wrapper.wrapDataSource(dataSource);
		assertThat(wrapped).isInstanceOf(AtomikosDataSourceBean.class);
		assertThat(((AtomikosDataSourceBean) wrapped).getXaDataSource()).isSameAs(dataSource);
	}

}
