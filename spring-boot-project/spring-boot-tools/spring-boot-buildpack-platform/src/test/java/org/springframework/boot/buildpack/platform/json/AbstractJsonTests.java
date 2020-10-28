package org.springframework.boot.buildpack.platform.json;

import java.io.InputStream;

import com.fasterxml.jackson.databind.ObjectMapper;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Abstract base class for JSON based tests.
 *

 */
public abstract class AbstractJsonTests {

	protected final ObjectMapper getObjectMapper() {
		return SharedObjectMapper.get();
	}

	protected final InputStream getContent(String name) {
		InputStream result = getClass().getResourceAsStream(name);
		assertThat(result).as("JSON source " + name).isNotNull();
		return result;
	}

}
