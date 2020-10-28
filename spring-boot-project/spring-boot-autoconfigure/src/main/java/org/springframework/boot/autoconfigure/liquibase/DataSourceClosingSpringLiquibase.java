package org.springframework.boot.autoconfigure.liquibase;

import java.lang.reflect.Method;

import javax.sql.DataSource;

import liquibase.exception.LiquibaseException;
import liquibase.integration.spring.SpringLiquibase;

import org.springframework.beans.factory.DisposableBean;
import org.springframework.util.ReflectionUtils;

/**
 * A custom {@link SpringLiquibase} extension that closes the underlying
 * {@link DataSource} once the database has been migrated.
 *

 * @since 2.0.6
 */
public class DataSourceClosingSpringLiquibase extends SpringLiquibase implements DisposableBean {

	private volatile boolean closeDataSourceOnceMigrated = true;

	public void setCloseDataSourceOnceMigrated(boolean closeDataSourceOnceMigrated) {
		this.closeDataSourceOnceMigrated = closeDataSourceOnceMigrated;
	}

	@Override
	public void afterPropertiesSet() throws LiquibaseException {
		super.afterPropertiesSet();
		if (this.closeDataSourceOnceMigrated) {
			closeDataSource();
		}
	}

	private void closeDataSource() {
		Class<?> dataSourceClass = getDataSource().getClass();
		Method closeMethod = ReflectionUtils.findMethod(dataSourceClass, "close");
		if (closeMethod != null) {
			ReflectionUtils.invokeMethod(closeMethod, getDataSource());
		}
	}

	@Override
	public void destroy() throws Exception {
		if (!this.closeDataSourceOnceMigrated) {
			closeDataSource();
		}
	}

}
