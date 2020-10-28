package org.springframework.boot.actuate.autoconfigure.web.jersey;

import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.servlet.ServletContainer;

import org.springframework.boot.autoconfigure.web.servlet.JerseyApplicationPath;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Shared configuration for Jersey-based actuators regardless of management context type.
 *

 */
@Configuration(proxyBeanMethods = false)
class JerseyManagementContextConfiguration {

	@Bean
	ServletRegistrationBean<ServletContainer> jerseyServletRegistration(JerseyApplicationPath jerseyApplicationPath,
			ResourceConfig resourceConfig) {
		return new ServletRegistrationBean<>(new ServletContainer(resourceConfig),
				jerseyApplicationPath.getUrlMapping());
	}

	@Bean
	ResourceConfig resourceConfig() {
		return new ResourceConfig();
	}

}
