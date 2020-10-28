package org.springframework.boot.jta.bitronix;

import java.sql.Connection;
import java.sql.SQLFeatureNotSupportedException;
import java.util.logging.Logger;

import javax.sql.XAConnection;
import javax.sql.XADataSource;

import bitronix.tm.TransactionManagerServices;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

/**
 * Tests for {@link PoolingDataSourceBean}.
 *

 */
@Deprecated
class PoolingDataSourceBeanTests {

	private PoolingDataSourceBean bean = new PoolingDataSourceBean();

	@Test
	void sensibleDefaults() {
		assertThat(this.bean.getMaxPoolSize()).isEqualTo(10);
		assertThat(this.bean.getAutomaticEnlistingEnabled()).isTrue();
		assertThat(this.bean.isEnableJdbc4ConnectionTest()).isTrue();
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
	void shouldReturnGlobalLoggerWhenDataSourceIsAbsent() throws SQLFeatureNotSupportedException {
		assertThat(this.bean.getParentLogger()).isSameAs(Logger.getLogger(Logger.GLOBAL_LOGGER_NAME));
	}

	@Test
	void shouldReturnGlobalLoggerWhenDataSourceThrowsException() throws SQLFeatureNotSupportedException {
		XADataSource dataSource = mock(XADataSource.class);
		given(dataSource.getParentLogger()).willThrow(new SQLFeatureNotSupportedException());
		this.bean.setDataSource(dataSource);
		assertThat(this.bean.getParentLogger()).isSameAs(Logger.getLogger(Logger.GLOBAL_LOGGER_NAME));
	}

	@Test
	void shouldReturnParentLoggerFromDataSource() throws SQLFeatureNotSupportedException {
		Logger logger = Logger.getLogger("test");
		XADataSource dataSource = mock(XADataSource.class);
		given(dataSource.getParentLogger()).willReturn(logger);
		this.bean.setDataSource(dataSource);
		assertThat(this.bean.getParentLogger()).isSameAs(logger);
	}

	@Test
	void setDataSource() throws Exception {
		XADataSource dataSource = mock(XADataSource.class);
		XAConnection xaConnection = mock(XAConnection.class);
		Connection connection = mock(Connection.class);
		given(dataSource.getXAConnection()).willReturn(xaConnection);
		given(xaConnection.getConnection()).willReturn(connection);
		this.bean.setDataSource(dataSource);
		this.bean.setBeanName("beanName");
		this.bean.afterPropertiesSet();
		this.bean.init();
		this.bean.createPooledConnection(dataSource, this.bean);
		verify(dataSource).getXAConnection();
		TransactionManagerServices.getTaskScheduler().shutdown();
	}

}
