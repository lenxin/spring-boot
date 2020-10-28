package org.springframework.boot.autoconfigure.quartz;

import javax.sql.DataSource;

import org.springframework.scheduling.quartz.SchedulerFactoryBean;

/**
 * Callback interface that can be implemented by beans wishing to customize the Quartz
 * {@link SchedulerFactoryBean} before it is fully initialized, in particular to tune its
 * configuration.
 * <p>
 * For customization of the {@link DataSource} used by Quartz, use of
 * {@link QuartzDataSource @QuartzDataSource} is preferred. It will ensure consistent
 * customization of both the {@link SchedulerFactoryBean} and the
 * {@link QuartzDataSourceInitializer}.
 *

 * @since 2.0.0
 */
@FunctionalInterface
public interface SchedulerFactoryBeanCustomizer {

	/**
	 * Customize the {@link SchedulerFactoryBean}.
	 * @param schedulerFactoryBean the scheduler to customize
	 */
	void customize(SchedulerFactoryBean schedulerFactoryBean);

}
