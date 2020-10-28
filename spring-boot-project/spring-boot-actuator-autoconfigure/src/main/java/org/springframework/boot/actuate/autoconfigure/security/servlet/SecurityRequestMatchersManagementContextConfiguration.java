package org.springframework.boot.actuate.autoconfigure.security.servlet;

import org.glassfish.jersey.server.ResourceConfig;

import org.springframework.boot.actuate.autoconfigure.web.ManagementContextConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.boot.autoconfigure.security.servlet.AntPathRequestMatcherProvider;
import org.springframework.boot.autoconfigure.security.servlet.RequestMatcherProvider;
import org.springframework.boot.autoconfigure.web.servlet.DispatcherServletPath;
import org.springframework.boot.autoconfigure.web.servlet.JerseyApplicationPath;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.web.util.matcher.RequestMatcher;
import org.springframework.web.servlet.DispatcherServlet;

/**
 * {@link ManagementContextConfiguration} that configures the appropriate
 * {@link RequestMatcherProvider}.
 *

 * @since 2.1.8
 */
@ManagementContextConfiguration(proxyBeanMethods = false)
@ConditionalOnClass({ RequestMatcher.class })
@ConditionalOnWebApplication(type = ConditionalOnWebApplication.Type.SERVLET)
public class SecurityRequestMatchersManagementContextConfiguration {

	@Configuration(proxyBeanMethods = false)
	@ConditionalOnClass(DispatcherServlet.class)
	@ConditionalOnBean(DispatcherServletPath.class)
	public static class MvcRequestMatcherConfiguration {

		@Bean
		@ConditionalOnMissingBean
		@ConditionalOnClass(DispatcherServlet.class)
		public RequestMatcherProvider requestMatcherProvider(DispatcherServletPath servletPath) {
			return new AntPathRequestMatcherProvider(servletPath::getRelativePath);
		}

	}

	@Configuration(proxyBeanMethods = false)
	@ConditionalOnClass(ResourceConfig.class)
	@ConditionalOnMissingClass("org.springframework.web.servlet.DispatcherServlet")
	@ConditionalOnBean(JerseyApplicationPath.class)
	public static class JerseyRequestMatcherConfiguration {

		@Bean
		public RequestMatcherProvider requestMatcherProvider(JerseyApplicationPath applicationPath) {
			return new AntPathRequestMatcherProvider(applicationPath::getRelativePath);
		}

	}

}
