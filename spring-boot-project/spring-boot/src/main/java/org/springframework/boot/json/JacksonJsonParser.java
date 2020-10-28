package org.springframework.boot.json;

import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Thin wrapper to adapt Jackson 2 {@link ObjectMapper} to {@link JsonParser}.
 *

 * @since 1.0.0
 * @see JsonParserFactory
 */
public class JacksonJsonParser extends AbstractJsonParser {

	private static final MapTypeReference MAP_TYPE = new MapTypeReference();

	private static final ListTypeReference LIST_TYPE = new ListTypeReference();

	private ObjectMapper objectMapper; // Late binding

	/**
	 * Creates an instance with the specified {@link ObjectMapper}.
	 * @param objectMapper the object mapper to use
	 */
	public JacksonJsonParser(ObjectMapper objectMapper) {
		this.objectMapper = objectMapper;
	}

	/**
	 * Creates an instance with a default {@link ObjectMapper} that is created lazily.
	 */
	public JacksonJsonParser() {
	}

	@Override
	public Map<String, Object> parseMap(String json) {
		return tryParse(() -> getObjectMapper().readValue(json, MAP_TYPE), Exception.class);
	}

	@Override
	public List<Object> parseList(String json) {
		return tryParse(() -> getObjectMapper().readValue(json, LIST_TYPE), Exception.class);
	}

	private ObjectMapper getObjectMapper() {
		if (this.objectMapper == null) {
			this.objectMapper = new ObjectMapper();
		}
		return this.objectMapper;
	}

	private static class MapTypeReference extends TypeReference<Map<String, Object>> {

	}

	private static class ListTypeReference extends TypeReference<List<Object>> {

	}

}
