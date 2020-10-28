package org.springframework.boot.actuate.endpoint.web.annotation;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.springframework.boot.actuate.endpoint.invoke.ParameterValueMapper;
import org.springframework.boot.actuate.endpoint.invoke.convert.ConversionServiceParameterValueMapper;
import org.springframework.boot.actuate.endpoint.web.EndpointMediaTypes;
import org.springframework.boot.web.embedded.tomcat.TomcatEmbeddedWebappClassLoader;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.core.convert.support.DefaultConversionService;

import static org.mockito.Mockito.mock;

/**
 * Base configuration shared by tests.
 *

 */
@Configuration(proxyBeanMethods = false)
class BaseConfiguration {

	@Bean
	AbstractWebEndpointIntegrationTests.EndpointDelegate endpointDelegate() {
		ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
		if (classLoader instanceof TomcatEmbeddedWebappClassLoader) {
			Thread.currentThread().setContextClassLoader(classLoader.getParent());
		}
		try {
			return mock(AbstractWebEndpointIntegrationTests.EndpointDelegate.class);
		}
		finally {
			Thread.currentThread().setContextClassLoader(classLoader);
		}
	}

	@Bean
	EndpointMediaTypes endpointMediaTypes() {
		List<String> mediaTypes = Arrays.asList("application/vnd.test+json", "application/json");
		return new EndpointMediaTypes(mediaTypes, mediaTypes);
	}

	@Bean
	WebEndpointDiscoverer webEndpointDiscoverer(EndpointMediaTypes endpointMediaTypes,
			ApplicationContext applicationContext) {
		ParameterValueMapper parameterMapper = new ConversionServiceParameterValueMapper(
				DefaultConversionService.getSharedInstance());
		return new WebEndpointDiscoverer(applicationContext, parameterMapper, endpointMediaTypes, null,
				Collections.emptyList(), Collections.emptyList());
	}

	@Bean
	PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurer() {
		return new PropertySourcesPlaceholderConfigurer();
	}

}
