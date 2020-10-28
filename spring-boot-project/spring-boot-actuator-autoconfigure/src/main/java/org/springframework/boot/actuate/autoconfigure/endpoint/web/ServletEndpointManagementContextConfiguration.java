package org.springframework.boot.actuate.autoconfigure.endpoint.web;

import org.glassfish.jersey.server.ResourceConfig;

import org.springframework.boot.actuate.autoconfigure.endpoint.expose.IncludeExcludeEndpointFilter;
import org.springframework.boot.actuate.autoconfigure.web.ManagementContextConfiguration;
import org.springframework.boot.actuate.endpoint.web.ExposableServletEndpoint;
import org.springframework.boot.actuate.endpoint.web.ServletEndpointRegistrar;
import org.springframework.boot.actuate.endpoint.web.annotation.ServletEndpointsSupplier;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication.Type;
import org.springframework.boot.autoconfigure.web.servlet.DispatcherServletPath;
import org.springframework.boot.autoconfigure.web.servlet.JerseyApplicationPath;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.DispatcherServlet;

/**
 * {@link ManagementContextConfiguration @ManagementContextConfiguration} for servlet
 * endpoints.
 *



 * @since 2.0.0
 */
@ManagementContextConfiguration(proxyBeanMethods = false)
@ConditionalOnWebApplication(type = Type.SERVLET)
public class ServletEndpointManagementContextConfiguration {

	@Bean
	public IncludeExcludeEndpointFilter<ExposableServletEndpoint> servletExposeExcludePropertyEndpointFilter(
			WebEndpointProperties properties) {
		WebEndpointProperties.Exposure exposure = properties.getExposure();
		return new IncludeExcludeEndpointFilter<>(ExposableServletEndpoint.class, exposure.getInclude(),
				exposure.getExclude());
	}

	@Configuration(proxyBeanMethods = false)
	@ConditionalOnClass(DispatcherServlet.class)
	public static class WebMvcServletEndpointManagementContextConfiguration {

		@Bean
		public ServletEndpointRegistrar servletEndpointRegistrar(WebEndpointProperties properties,
				ServletEndpointsSupplier servletEndpointsSupplier, DispatcherServletPath dispatcherServletPath) {
			return new ServletEndpointRegistrar(dispatcherServletPath.getRelativePath(properties.getBasePath()),
					servletEndpointsSupplier.getEndpoints());
		}

	}

	@Configuration(proxyBeanMethods = false)
	@ConditionalOnClass(ResourceConfig.class)
	@ConditionalOnMissingClass("org.springframework.web.servlet.DispatcherServlet")
	public static class JerseyServletEndpointManagementContextConfiguration {

		@Bean
		public ServletEndpointRegistrar servletEndpointRegistrar(WebEndpointProperties properties,
				ServletEndpointsSupplier servletEndpointsSupplier, JerseyApplicationPath jerseyApplicationPath) {
			return new ServletEndpointRegistrar(jerseyApplicationPath.getRelativePath(properties.getBasePath()),
					servletEndpointsSupplier.getEndpoints());
		}

	}

}
