package org.springframework.boot.actuate.autoconfigure.web.servlet;

import javax.servlet.Servlet;

import org.springframework.boot.actuate.autoconfigure.endpoint.web.WebEndpointProperties;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication.Type;
import org.springframework.boot.web.servlet.filter.ApplicationContextHeaderFilter;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * {@link EnableAutoConfiguration Auto-configuration} for Servlet-specific management
 * context concerns.
 *

 * @since 2.0.0
 */
@Configuration(proxyBeanMethods = false)
@ConditionalOnClass(Servlet.class)
@ConditionalOnWebApplication(type = Type.SERVLET)
public class ServletManagementContextAutoConfiguration {

	@Bean
	public ServletManagementContextFactory servletWebChildContextFactory() {
		return new ServletManagementContextFactory();
	}

	@Bean
	public ManagementServletContext managementServletContext(WebEndpointProperties properties) {
		return properties::getBasePath;
	}

	// Put Servlets and Filters in their own nested class so they don't force early
	// instantiation of ManagementServerProperties.
	@Configuration(proxyBeanMethods = false)
	@ConditionalOnProperty(prefix = "management.server", name = "add-application-context-header", havingValue = "true")
	protected static class ApplicationContextFilterConfiguration {

		@Bean
		public ApplicationContextHeaderFilter applicationContextIdFilter(ApplicationContext context) {
			return new ApplicationContextHeaderFilter(context);
		}

	}

}
