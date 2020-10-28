package org.springframework.boot.autoconfigure.data.rest;

import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.data.rest.core.config.RepositoryRestConfiguration;
import org.springframework.data.rest.webmvc.config.RepositoryRestConfigurer;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
import org.springframework.web.servlet.config.annotation.CorsRegistry;

/**
 * A {@code RepositoryRestConfigurer} that applies configuration items from the
 * {@code spring.data.rest} namespace to Spring Data REST. Also, if a
 * {@link Jackson2ObjectMapperBuilder} is available, it is used to configure Spring Data
 * REST's {@link ObjectMapper ObjectMappers}.
 *


 */
@Order(0)
class SpringBootRepositoryRestConfigurer implements RepositoryRestConfigurer {

	@Autowired(required = false)
	private Jackson2ObjectMapperBuilder objectMapperBuilder;

	@Autowired
	private RepositoryRestProperties properties;

	@Override
	@SuppressWarnings("deprecation")
	public void configureRepositoryRestConfiguration(RepositoryRestConfiguration config) {
		configureRepositoryRestConfiguration(config, null);
	}

	@Override
	public void configureRepositoryRestConfiguration(RepositoryRestConfiguration config, CorsRegistry cors) {
		this.properties.applyTo(config);
	}

	@Override
	public void configureJacksonObjectMapper(ObjectMapper objectMapper) {
		if (this.objectMapperBuilder != null) {
			this.objectMapperBuilder.configure(objectMapper);
		}
	}

}
