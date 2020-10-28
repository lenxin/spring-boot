package org.springframework.boot.actuate.health;

import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import org.springframework.boot.actuate.endpoint.web.WebEndpointResponse;
import org.springframework.util.CollectionUtils;

/**
 * Simple {@link HttpCodeStatusMapper} backed by map of {@link Status#getCode() status
 * code} to HTTP status code.
 *


 * @since 2.2.0
 */
public class SimpleHttpCodeStatusMapper implements HttpCodeStatusMapper {

	private static final Map<String, Integer> DEFAULT_MAPPINGS;
	static {
		Map<String, Integer> defaultMappings = new HashMap<>();
		defaultMappings.put(Status.DOWN.getCode(), WebEndpointResponse.STATUS_SERVICE_UNAVAILABLE);
		defaultMappings.put(Status.OUT_OF_SERVICE.getCode(), WebEndpointResponse.STATUS_SERVICE_UNAVAILABLE);
		DEFAULT_MAPPINGS = getUniformMappings(defaultMappings);
	}

	private final Map<String, Integer> mappings;

	/**
	 * Create a new {@link SimpleHttpCodeStatusMapper} instance using default mappings.
	 */
	public SimpleHttpCodeStatusMapper() {
		this(null);
	}

	/**
	 * Create a new {@link SimpleHttpCodeStatusMapper} with the specified mappings.
	 * @param mappings the mappings to use or {@code null} to use the default mappings
	 */
	public SimpleHttpCodeStatusMapper(Map<String, Integer> mappings) {
		this.mappings = CollectionUtils.isEmpty(mappings) ? DEFAULT_MAPPINGS : getUniformMappings(mappings);
	}

	@Override
	public int getStatusCode(Status status) {
		String code = getUniformCode(status.getCode());
		return this.mappings.getOrDefault(code, WebEndpointResponse.STATUS_OK);
	}

	private static Map<String, Integer> getUniformMappings(Map<String, Integer> mappings) {
		Map<String, Integer> result = new LinkedHashMap<>();
		for (Map.Entry<String, Integer> entry : mappings.entrySet()) {
			String code = getUniformCode(entry.getKey());
			if (code != null) {
				result.putIfAbsent(code, entry.getValue());
			}
		}
		return Collections.unmodifiableMap(result);
	}

	private static String getUniformCode(String code) {
		if (code == null) {
			return null;
		}
		StringBuilder builder = new StringBuilder();
		for (char ch : code.toCharArray()) {
			if (Character.isAlphabetic(ch) || Character.isDigit(ch)) {
				builder.append(Character.toLowerCase(ch));
			}
		}
		return builder.toString();
	}

}
