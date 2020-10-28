package org.springframework.boot.web.servlet.support;

import javax.servlet.DispatcherType;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Configuration for {@link ErrorPageFilter}.
 *

 */
@Configuration(proxyBeanMethods = false)
class ErrorPageFilterConfiguration {

	@Bean
	ErrorPageFilter errorPageFilter() {
		return new ErrorPageFilter();
	}

	@Bean
	FilterRegistrationBean<ErrorPageFilter> errorPageFilterRegistration(ErrorPageFilter filter) {
		FilterRegistrationBean<ErrorPageFilter> registration = new FilterRegistrationBean<>(filter);
		registration.setOrder(filter.getOrder());
		registration.setDispatcherTypes(DispatcherType.REQUEST, DispatcherType.ASYNC);
		return registration;
	}

}
