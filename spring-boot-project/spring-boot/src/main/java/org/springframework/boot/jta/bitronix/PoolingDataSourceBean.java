package org.springframework.boot.jta.bitronix;

import java.io.PrintWriter;
import java.sql.SQLException;
import java.sql.SQLFeatureNotSupportedException;
import java.util.Properties;
import java.util.logging.Logger;

import javax.sql.XAConnection;
import javax.sql.XADataSource;

import bitronix.tm.resource.common.ResourceBean;
import bitronix.tm.resource.common.XAStatefulHolder;
import bitronix.tm.resource.jdbc.PoolingDataSource;

import org.springframework.beans.factory.BeanNameAware;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.util.StringUtils;

/**
 * Spring friendly version of {@link PoolingDataSource}. Provides sensible defaults and
 * also supports direct wrapping of a {@link XADataSource} instance.
 *



 * @since 1.2.0
 * @deprecated since 2.3.0 as the Bitronix project is no longer being maintained
 */
@Deprecated
@SuppressWarnings("serial")
@ConfigurationProperties(prefix = "spring.jta.bitronix.datasource")
public class PoolingDataSourceBean extends PoolingDataSource implements BeanNameAware, InitializingBean {

	private static final ThreadLocal<PoolingDataSourceBean> source = new ThreadLocal<>();

	private XADataSource dataSource;

	private String beanName;

	public PoolingDataSourceBean() {
		setMaxPoolSize(10);
		setAllowLocalTransactions(true);
		setEnableJdbc4ConnectionTest(true);
	}

	@Override
	public synchronized void init() {
		source.set(this);
		try {
			super.init();
		}
		finally {
			source.remove();
		}
	}

	@Override
	public void setBeanName(String name) {
		this.beanName = name;
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		if (!StringUtils.hasLength(getUniqueName())) {
			setUniqueName(this.beanName);
		}
	}

	/**
	 * Set the {@link XADataSource} directly, instead of calling
	 * {@link #setClassName(String)}.
	 * @param dataSource the data source to use
	 */
	public void setDataSource(XADataSource dataSource) {
		this.dataSource = dataSource;
		setClassName(DirectXADataSource.class.getName());
		setDriverProperties(new Properties());
	}

	protected final XADataSource getDataSource() {
		return this.dataSource;
	}

	@Override
	public XAStatefulHolder createPooledConnection(Object xaFactory, ResourceBean bean) throws Exception {
		if (xaFactory instanceof DirectXADataSource) {
			xaFactory = ((DirectXADataSource) xaFactory).getDataSource();
		}
		return super.createPooledConnection(xaFactory, bean);
	}

	@Override
	public Logger getParentLogger() throws SQLFeatureNotSupportedException {
		XADataSource dataSource = this.dataSource;
		if (dataSource != null) {
			try {
				return dataSource.getParentLogger();
			}
			catch (Exception ex) {
				// Swallow and continue
			}
		}
		return Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);
	}

	/**
	 * A {@link XADataSource} implementation that delegates to the {@link ThreadLocal}
	 * {@link PoolingDataSourceBean}.
	 *
	 * @see PoolingDataSourceBean#setDataSource(XADataSource)
	 */
	public static class DirectXADataSource implements XADataSource {

		private final XADataSource dataSource;

		public DirectXADataSource() {
			this.dataSource = source.get().dataSource;
		}

		@Override
		public PrintWriter getLogWriter() throws SQLException {
			return this.dataSource.getLogWriter();
		}

		@Override
		public XAConnection getXAConnection() throws SQLException {
			return this.dataSource.getXAConnection();
		}

		@Override
		public XAConnection getXAConnection(String user, String password) throws SQLException {
			return this.dataSource.getXAConnection(user, password);
		}

		@Override
		public void setLogWriter(PrintWriter out) throws SQLException {
			this.dataSource.setLogWriter(out);
		}

		@Override
		public void setLoginTimeout(int seconds) throws SQLException {
			this.dataSource.setLoginTimeout(seconds);
		}

		@Override
		public int getLoginTimeout() throws SQLException {
			return this.dataSource.getLoginTimeout();
		}

		@Override
		public Logger getParentLogger() throws SQLFeatureNotSupportedException {
			return this.dataSource.getParentLogger();
		}

		public XADataSource getDataSource() {
			return this.dataSource;
		}

	}

}
