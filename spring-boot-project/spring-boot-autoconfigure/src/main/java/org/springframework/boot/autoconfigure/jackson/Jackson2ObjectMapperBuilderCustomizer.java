package org.springframework.boot.autoconfigure.jackson;

import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;

/**
 * Callback interface that can be implemented by beans wishing to further customize the
 * {@link ObjectMapper} via {@link Jackson2ObjectMapperBuilder} retaining its default
 * auto-configuration.
 *

 * @since 1.4.0
 */
@FunctionalInterface
public interface Jackson2ObjectMapperBuilderCustomizer {

	/**
	 * Customize the JacksonObjectMapperBuilder.
	 * @param jacksonObjectMapperBuilder the JacksonObjectMapperBuilder to customize
	 */
	void customize(Jackson2ObjectMapperBuilder jacksonObjectMapperBuilder);

}
