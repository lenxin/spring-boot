package org.springframework.boot.buildpack.platform.json;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.module.paramnames.ParameterNamesModule;

/**
 * Provides access to a shared pre-configured {@link ObjectMapper}.
 *

 * @since 2.3.0
 */
public final class SharedObjectMapper {

	private static final ObjectMapper INSTANCE;

	static {
		ObjectMapper objectMapper = new ObjectMapper();
		objectMapper.registerModule(new ParameterNamesModule());
		objectMapper.enable(SerializationFeature.INDENT_OUTPUT);
		objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		objectMapper.setPropertyNamingStrategy(PropertyNamingStrategy.LOWER_CAMEL_CASE);
		INSTANCE = objectMapper;
	}

	private SharedObjectMapper() {
	}

	public static ObjectMapper get() {
		return INSTANCE;
	}

}
