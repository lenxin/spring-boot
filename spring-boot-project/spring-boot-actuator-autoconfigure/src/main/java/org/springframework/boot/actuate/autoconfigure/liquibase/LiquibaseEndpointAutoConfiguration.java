package org.springframework.boot.actuate.autoconfigure.liquibase;

import liquibase.integration.spring.SpringLiquibase;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.boot.actuate.autoconfigure.endpoint.condition.ConditionalOnAvailableEndpoint;
import org.springframework.boot.actuate.liquibase.LiquibaseEndpoint;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.liquibase.DataSourceClosingSpringLiquibase;
import org.springframework.boot.autoconfigure.liquibase.LiquibaseAutoConfiguration;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * {@link EnableAutoConfiguration Auto-configuration} for {@link LiquibaseEndpoint}.
 *

 * @since 2.0.0
 */
@Configuration(proxyBeanMethods = false)
@ConditionalOnClass(SpringLiquibase.class)
@ConditionalOnAvailableEndpoint(endpoint = LiquibaseEndpoint.class)
@AutoConfigureAfter(LiquibaseAutoConfiguration.class)
public class LiquibaseEndpointAutoConfiguration {

	@Bean
	@ConditionalOnBean(SpringLiquibase.class)
	@ConditionalOnMissingBean
	public LiquibaseEndpoint liquibaseEndpoint(ApplicationContext context) {
		return new LiquibaseEndpoint(context);
	}

	@Bean
	@ConditionalOnBean(SpringLiquibase.class)
	public static BeanPostProcessor preventDataSourceCloseBeanPostProcessor() {
		return new BeanPostProcessor() {

			@Override
			public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
				if (bean instanceof DataSourceClosingSpringLiquibase) {
					((DataSourceClosingSpringLiquibase) bean).setCloseDataSourceOnceMigrated(false);
				}
				return bean;
			}

			@Override
			public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
				return bean;
			}

		};
	}

}
